package io.nop.biz.crud;

import io.nop.api.core.beans.FilterBeanConstants;
import io.nop.api.core.beans.query.OrderFieldBean;
import io.nop.api.core.beans.query.QueryBean;
import io.nop.commons.util.StringHelper;
import io.nop.core.context.IEvalContext;
import io.nop.core.lang.eval.IEvalFunction;
import io.nop.graphql.core.GraphQLConstants;
import io.nop.xlang.xmeta.IObjMeta;
import io.nop.xlang.xmeta.IObjPropMeta;

public class BizQueryHelper {
    public static void transformFilter(QueryBean query, IObjMeta objMeta, IEvalContext ctx) {
        query.transformFilter(filter -> {
            String name = (String) filter.getAttr(FilterBeanConstants.FILTER_ATTR_NAME);
            if (StringHelper.isEmpty(name)) {
                return filter;
            }
            IObjPropMeta propMeta = objMeta.getProp(name);
            if (propMeta == null)
                return filter;

            IEvalFunction fn = (IEvalFunction) propMeta.prop_get(GraphQLConstants.TAG_GRAPHQL_TRANS_FILTER);
            if (fn == null)
                return filter;

            return fn.call3(null, filter, query, false, ctx.getEvalScope());
        });
    }

    public static void transformMapToProp(QueryBean query, IObjMeta objMeta) {
        if (!objMeta.hasMapToProp())
            return;

        if (query.getFilter() != null) {
            query.getFilter().cascadeVisit(node -> {
                String name = (String) node.getAttr(FilterBeanConstants.FILTER_ATTR_NAME);
                if (name != null) {
                    IObjPropMeta propMeta = objMeta.getProp(name);
                    if (propMeta != null && propMeta.getMapToProp() != null) {
                        node.setAttr(FilterBeanConstants.FILTER_ATTR_NAME, propMeta.getMapToProp());
                    }
                }
            });
        }

        if (query.getOrderBy() != null) {
            for (OrderFieldBean orderField : query.getOrderBy()) {
                IObjPropMeta propMeta = objMeta.getProp(orderField.getName());
                if (propMeta != null && propMeta.getMapToProp() != null) {
                    orderField.setName(propMeta.getMapToProp());
                }
            }
        }
    }
}
