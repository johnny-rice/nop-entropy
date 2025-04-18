/**
 * Copyright (c) 2017-2024 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-entropy
 * Github: https://github.com/entropy-cloud/nop-entropy
 */
package io.nop.ioc.impl.resolvers;

import io.nop.commons.util.ClassHelper;
import io.nop.core.lang.xml.XNode;
import io.nop.ioc.api.IBeanContainerImplementor;
import io.nop.ioc.api.IBeanScope;
import io.nop.ioc.impl.IBeanPropValueResolver;

import java.util.List;
import java.util.Set;

public class ListValueResolver implements IBeanPropValueResolver {
    private final Class<?> type;
    private final List<IBeanPropValueResolver> items;
    private final boolean excludeNull;

    public ListValueResolver(Class<?> type, List<IBeanPropValueResolver> items, boolean excludeNull) {
        this.type = type;
        this.items = items;
        this.excludeNull = excludeNull;
    }

    @Override
    public String toConfigString() {
        return null;
    }

    @Override
    public XNode toConfigNode() {
        XNode node = XNode.make("list");
        for (IBeanPropValueResolver item : items) {
            node.appendChild(item.toConfigNode());
        }
        return node;
    }

    @Override
    public List<Object> resolveValue(IBeanContainerImplementor container, IBeanScope scope) {
        List<Object> ret = (List<Object>) ClassHelper.newInstance(type);

        for (IBeanPropValueResolver item : items) {
            Object value = item.resolveValue(container, scope);
            if (excludeNull && value == null)
                continue;

            ret.add(value);
        }
        return ret;
    }

    @Override
    public void collectConfigVars(Set<String> vars, boolean reactive) {
        for (IBeanPropValueResolver resolver : items) {
            resolver.collectConfigVars(vars, reactive);
        }
    }

    @Override
    public void collectDepends(Set<String> depends) {
        for (IBeanPropValueResolver resolver : items) {
            resolver.collectDepends(depends);
        }
    }
}