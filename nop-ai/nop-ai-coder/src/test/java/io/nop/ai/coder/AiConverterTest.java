package io.nop.ai.coder;

import io.nop.autotest.junit.JunitBaseTestCase;
import io.nop.converter.DocumentConvertOptions;
import io.nop.converter.DocumentConverterManager;
import io.nop.converter.registration.ConverterRegistrationBean;
import io.nop.core.resource.IResource;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AiConverterTest extends JunitBaseTestCase {
    @Inject
    ConverterRegistrationBean registrationBean;

    @Test
    public void testConvertOrm() {
        IResource aiOrmResource = attachmentResource("test.ai-orm.xml");
        IResource ormResource = getTargetResource("result/test.orm.xml");
        IResource xlsxResource = getTargetResource("result/test.orm.xlsx");
        IResource javaResource = getTargetResource("result/test.orm.java");

        DocumentConvertOptions options = DocumentConvertOptions.create().allowChained();
        DocumentConverterManager manager = DocumentConverterManager.instance();
        manager.convertResource(aiOrmResource, ormResource, options);
        manager.convertResource(aiOrmResource, xlsxResource, options);
        manager.convertResource(aiOrmResource, javaResource, options);
    }

    @Test
    public void testConvertXDef() {
        IResource resource = getResource("/nop/ai/schema/coder/workbook.xdef");
        IResource toResource = getTargetResource("result/workbook.ai-xdef.xml");

        DocumentConverterManager manager = DocumentConverterManager.instance();
        DocumentConvertOptions options = DocumentConvertOptions.create();

        manager.convertResource(resource, toResource, options);
        assertTrue(toResource.readText().contains("horizontalAlign=\"enum:general|left|center|right|fill|justify|centerSelection|distributed\""));
    }

    @Test
    public void testConvertExcel(){
        IResource resource = attachmentResource("test.workbook.xml");
        IResource toResource = getTargetResource("result/result.xlsx");

        DocumentConverterManager manager = DocumentConverterManager.instance();
        DocumentConvertOptions options = DocumentConvertOptions.create();

        manager.convertResource(resource,toResource, options);
    }
}
