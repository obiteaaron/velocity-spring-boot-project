package com.alibaba.boot.velocity.web.servlet.view;

import org.apache.velocity.context.Context;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolManager;
import org.apache.velocity.tools.Toolbox;
import org.apache.velocity.tools.view.ViewToolContext;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.boot.web.servlet.view.velocity.EmbeddedVelocityToolboxView;
import org.springframework.web.servlet.view.velocity.VelocityConfig;
import org.springframework.web.servlet.view.velocity.VelocityLayoutView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * {@link EmbeddedVelocityLayoutView}
 *
 * @author luyan
 * @see VelocityLayoutView
 * @see EmbeddedVelocityToolboxView
 * @since 2019/12/9 10:58
 */
public class EmbeddedVelocityLayoutView extends VelocityLayoutView {
    @Override
    protected Context createVelocityContext(Map<String, Object> model,
                                            HttpServletRequest request, HttpServletResponse response)
            throws Exception {// Create a
        // ChainedContext
        // instance.
        ViewToolContext ctx;

        ctx = new ViewToolContext(getVelocityEngine(), request, response,
                getServletContext());

        ctx.putAll(model);

        if (this.getToolboxConfigLocation() != null) {
            ToolManager tm = BeanFactoryUtils.beanOfTypeIncludingAncestors(
                    getApplicationContext(), ToolManager.class, true, false);

            if (tm.getToolboxFactory().hasTools(Scope.APPLICATION)) {
                Toolbox toolbox = getApplicationContext().getBean("applicationToolbox", Toolbox.class);
                ctx.addToolbox(toolbox);
            }
            if (tm.getToolboxFactory().hasTools(Scope.REQUEST)) {
                ctx.addToolbox(tm.getToolboxFactory().createToolbox(
                        Scope.REQUEST));
            }
            if (tm.getToolboxFactory().hasTools(Scope.SESSION)) {
                ctx.addToolbox(tm.getToolboxFactory().createToolbox(
                        Scope.SESSION));
            }
        }
        return ctx;
    }
}


