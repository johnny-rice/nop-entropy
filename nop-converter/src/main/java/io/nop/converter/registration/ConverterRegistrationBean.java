package io.nop.converter.registration;

import io.nop.api.core.config.AppConfig;
import io.nop.api.core.exceptions.NopException;
import io.nop.commons.util.ClassHelper;
import io.nop.commons.util.StringHelper;
import io.nop.converter.DocConvertConstants;
import io.nop.converter.DocumentConverterManager;
import io.nop.converter.IDocumentConverter;
import io.nop.converter.IDocumentObjectBuilder;
import io.nop.converter.config.ConvertBuilderConfig;
import io.nop.converter.config.ConvertConfig;
import io.nop.converter.config.ConvertConverterConfig;
import io.nop.converter.impl.ConvertModelBuilder;
import io.nop.converter.impl.DslDocumentConverter;
import io.nop.converter.impl.DslDocumentObjectBuilder;
import io.nop.converter.impl.ExcelAdapter;
import io.nop.core.resource.IResource;
import io.nop.core.resource.ResourceHelper;
import io.nop.core.resource.VirtualFileSystem;
import io.nop.core.resource.component.ComponentModelConfig;
import io.nop.core.resource.component.ResourceComponentManager;
import io.nop.xlang.xdsl.DslModelHelper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ConverterRegistrationBean {
    static final Logger LOG = LoggerFactory.getLogger(ConverterRegistrationBean.class);

    @PostConstruct
    public void register() {
        ConvertConfig config = loadMergedConfig();

        DocumentConverterManager registry = DocumentConverterManager.instance();
        if (config.getBuilders() != null) {
            config.getBuilders().forEach(builder -> {
                registerBuilder(registry, builder);
            });
        }

        Map<String, Map<String, IDocumentConverter>> allConverters = new HashMap<>();

        if (config.getConverters() != null) {
            config.getConverters().forEach(converterConfig -> {
                IDocumentConverter converter = newConverter(converterConfig);
                if (converter != null) {
                    putConverter(allConverters, converterConfig.getFrom(), converterConfig.getTo(), converter, false);
                }
            });
        }

        loadDslConverters(registry, allConverters);

        allConverters.forEach((fromFileType, map) -> {
            map.forEach((toFileType, converter) -> {
                registry.registerConverter(fromFileType, toFileType, converter);
            });
        });

        if (AppConfig.isDebugMode()) {
            IResource resource = ResourceHelper.getDumpResource("/nop/converter/registry/merged-all.convert.xml");
            ResourceHelper.writeXml(resource, new ConvertModelBuilder().buildConvertNode(registry));
        }
    }

    ConvertConfig loadMergedConfig() {
        Collection<? extends IResource> resources = VirtualFileSystem.instance().getAllResources("/nop/converter/registry", ".convert.xml");

        ConvertConfig config = null;
        for (IResource resource : resources) {
            ConvertConfig subConfig = (ConvertConfig) DslModelHelper.loadDslModel(resource);
            if (config == null) {
                config = subConfig;
            } else {
                config.merge(subConfig);
            }
        }
        if (config == null)
            config = new ConvertConfig();
        return config;
    }

    protected void registerBuilder(DocumentConverterManager registry, ConvertBuilderConfig builderConfig) {
        try {
            IDocumentObjectBuilder builder = (IDocumentObjectBuilder) ClassHelper.newInstance(
                    builderConfig.getClassName(), IDocumentObjectBuilder.class.getClassLoader());
            registry.registerDocumentObjectBuilder(builderConfig.getFileType(), builder);
        } catch (Exception e) {
            if (builderConfig.isOptional()) {
                LOG.info("nop.convert.new-builder-fail", e);
            } else {
                throw NopException.adapt(e);
            }
        }
    }

    protected IDocumentConverter newConverter(ConvertConverterConfig convertConfig) {
        try {
            IDocumentConverter converter = (IDocumentConverter) ClassHelper.newInstance(
                    convertConfig.getClassName(), IDocumentConverter.class.getClassLoader());
            return converter;
        } catch (Exception e) {
            if (convertConfig.isOptional()) {
                LOG.info("nop.convert.new-converter-fail", e);
                return null;
            } else {
                throw NopException.adapt(e);
            }
        }
    }

    protected void loadDslConverters(DocumentConverterManager registry,
                                     Map<String, Map<String, IDocumentConverter>> allConverters) {
        Collection<ComponentModelConfig> modelConfigs = ResourceComponentManager.instance().getAllModelConfigs().values();
        for (ComponentModelConfig modelConfig : modelConfigs) {
            for (String fromFileType : modelConfig.getLoaders().keySet()) {
                String fromFileExt = StringHelper.fileExtFromFileType(fromFileType);
                ComponentModelConfig.LoaderConfig fromConfig = modelConfig.getLoader(fromFileType);

                if (DocConvertConstants.FILE_TYPE_XLSX.equals(fromFileExt)) {
                    if (!DslModelHelper.supportExcelModelLoader()) {
                        continue;
                    }
                    if (fromConfig.getImpPath() == null)
                        continue;
                    IDocumentObjectBuilder builder = ExcelAdapter.newXlsxDslDocumentObjectBuilder();
                    registry.registerDocumentObjectBuilder(fromFileType, builder);
                } else {
                    if (fromConfig.getXdefPath() == null)
                        continue;
                    IDocumentObjectBuilder builder = new DslDocumentObjectBuilder();
                    registry.registerDocumentObjectBuilder(fromFileType, builder);
                }

                for (String toFileType : modelConfig.getLoaders().keySet()) {
                    if (fromFileType.equals(toFileType))
                        continue;

                    Map<String, IDocumentConverter> map = allConverters.computeIfAbsent(fromFileType, k -> new HashMap<>());
                    // 已经存在映射，则这里忽略
                    if (map.containsKey(toFileType)) {
                        LOG.info("nop.ignore-dsl-converter:fromFileType={}, toFileType={}",
                                fromFileType, toFileType);
                        continue;
                    }

                    ComponentModelConfig.LoaderConfig toConfig = modelConfig.getLoader(toFileType);
                    IDocumentConverter converter = newConverter(toFileType, fromConfig, toConfig);
                    if (converter != null)
                        registry.registerConverter(fromFileType, toFileType, converter);
                }
            }
        }
    }

    protected IDocumentConverter newConverter(String toFileType, ComponentModelConfig.LoaderConfig fromConfig,
                                              ComponentModelConfig.LoaderConfig toConfig) {
        String toFileExt = StringHelper.fileExtFromFileType(toFileType);
        if (DocConvertConstants.FILE_TYPE_XLSX.equals(toFileExt)) {
            if (!DslModelHelper.supportExcelModelLoader()) {
                return null;
            }
            if (toConfig.getImpPath() == null)
                return null;
            return ExcelAdapter.newDslToExcelDocumentConverter();
        } else {
            String xdefPath = toConfig.getXdefPath();
            if (xdefPath == null)
                return null;

            return new DslDocumentConverter();
        }
    }

    protected void putConverter(Map<String, Map<String, IDocumentConverter>> allConverters,
                                String fromFileType, String toFileType, IDocumentConverter converter,
                                boolean allowOverride) {
        Map<String, IDocumentConverter> map = allConverters.computeIfAbsent(fromFileType, k -> new HashMap<>());
        if (!allowOverride && map.containsKey(toFileType)) {
            throw new IllegalArgumentException("Converter already exists for fromFileType: " + fromFileType +
                    ", toFileType: " + toFileType);
        }
        map.put(toFileType, converter);
    }
}
