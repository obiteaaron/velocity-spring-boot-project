package com.alibaba.boot.velocity.web.servlet.view;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolManager;
import org.apache.velocity.tools.view.ViewToolContext;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.boot.web.servlet.view.velocity.EmbeddedVelocityToolboxView;
import org.springframework.boot.web.servlet.view.velocity.EmbeddedVelocityViewResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.velocity.VelocityLayoutView;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * {@link EmbeddedVelocityLayoutView}
 *
 * @author Obite Aaron
 * @author <a href="mailto:obiteaaron@gmail.com">ObiteAaron</a>
 * @version 1.0.5
 * @see EmbeddedVelocityViewResolver
 * @see VelocityLayoutView
 * @see EmbeddedVelocityToolboxView
 * @since 1.0.5 2019-12-23
 */
public class EmbeddedVelocityLayoutView extends VelocityLayoutView {

    private boolean exposeModelAndViewToContext = false;

    private boolean allowModelAndViewToContextOverride = false;

    private boolean exposeModelAndViewToTool = false;

    private boolean allowModelAndViewToToolOverride = false;

    @Override
    protected Context createVelocityContext(Map<String, Object> model,
                                            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // Create a
        // ChainedContext
        // instance.
        EmbeddedViewToolContext context;

        context = new EmbeddedViewToolContext(getVelocityEngine(), request, response,
                getServletContext());

        context.putAll(model);
        mergeModelAndView(context, model);

        if (this.getToolboxConfigLocation() != null) {
            ToolManager tm = BeanFactoryUtils.beanOfTypeIncludingAncestors(
                    getApplicationContext(), ToolManager.class, true, false);

            if (tm.getToolboxFactory().hasTools(Scope.APPLICATION)) {
                context.addToolbox(tm.getToolboxFactory().createToolbox(
                        Scope.APPLICATION));
            }
            if (tm.getToolboxFactory().hasTools(Scope.REQUEST)) {
                context.addToolbox(tm.getToolboxFactory().createToolbox(
                        Scope.REQUEST));
            }
            if (tm.getToolboxFactory().hasTools(Scope.SESSION)) {
                context.addToolbox(tm.getToolboxFactory().createToolbox(
                        Scope.SESSION));
            }
        }
        return context;
    }

    private void mergeModelAndView(EmbeddedViewToolContext context, Map<String, Object> model) throws ServletException {
        if (exposeModelAndViewToContext) {
            Object modelAndView = model.get("modelAndView");
            if (modelAndView instanceof ModelAndView) {
                for (Map.Entry<String, Object> entry : ((ModelAndView) modelAndView).getModel().entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (context.containsKey(key) && !this.allowModelAndViewToContextOverride) {
                        throw new ServletException("Cannot expose modelAndView attribute '" + key +
                                "' because of an existing velocity context object of the same name");
                    }
                    context.put(key, value);
                    if (logger.isDebugEnabled()) {
                        logger.debug("Exposing modelAndView attribute '" + key +
                                "' with value [" + value + "] to velocity context");
                    }
                }
            }
        }

        if (exposeModelAndViewToTool) {
            Object modelAndView = model.get("modelAndView");
            if (modelAndView instanceof ModelAndView) {
                for (Map.Entry<String, Object> entry : ((ModelAndView) modelAndView).getModel().entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (context.getToolProperties().containsKey(key) && !this.allowModelAndViewToToolOverride) {
                        throw new ServletException("Cannot expose modelAndView attribute '" + key +
                                "' because of an existing velocity tool context object of the same name");
                    }
                    context.putToolProperty(key, value);
                    if (logger.isDebugEnabled()) {
                        logger.debug("Exposing modelAndView attribute '" + key +
                                "' with value [" + value + "] to velocity tool context");
                    }
                }
            }
        }
    }

    public boolean isExposeModelAndViewToContext() {
        return exposeModelAndViewToContext;
    }

    public void setExposeModelAndViewToContext(boolean exposeModelAndViewToContext) {
        this.exposeModelAndViewToContext = exposeModelAndViewToContext;
    }

    public boolean isAllowModelAndViewToContextOverride() {
        return allowModelAndViewToContextOverride;
    }

    public void setAllowModelAndViewToContextOverride(boolean allowModelAndViewToContextOverride) {
        this.allowModelAndViewToContextOverride = allowModelAndViewToContextOverride;
    }

    public boolean isExposeModelAndViewToTool() {
        return exposeModelAndViewToTool;
    }

    public void setExposeModelAndViewToTool(boolean exposeModelAndViewToTool) {
        this.exposeModelAndViewToTool = exposeModelAndViewToTool;
    }

    public boolean isAllowModelAndViewToToolOverride() {
        return allowModelAndViewToToolOverride;
    }

    public void setAllowModelAndViewToToolOverride(boolean allowModelAndViewToToolOverride) {
        this.allowModelAndViewToToolOverride = allowModelAndViewToToolOverride;
    }

    public static class EmbeddedViewToolContext extends ViewToolContext {

        public EmbeddedViewToolContext(VelocityEngine velocity, HttpServletRequest request, HttpServletResponse response, ServletContext application) {
            super(velocity, request, response, application);
        }

        @Override
        protected Map<String, Object> getToolProperties() {
            return super.getToolProperties();
        }
    }
}


