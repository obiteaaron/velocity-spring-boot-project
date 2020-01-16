package com.alibaba.boot.velocity.web.servlet.view;

import org.springframework.boot.web.servlet.view.velocity.EmbeddedVelocityViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.velocity.VelocityLayoutViewResolver;

/**
 * {@link EmbeddedVelocityLayoutViewResolver}
 * <p>
 * Part Source Copied From {@link EmbeddedVelocityViewResolver}
 *
 * @author Obite Aaron
 * @author <a href="mailto:obiteaaron@gmail.com">ObiteAaron</a>
 * @version 1.0.5
 * @see EmbeddedVelocityViewResolver
 * @since 1.0.5 2019-12-23
 */
public class EmbeddedVelocityLayoutViewResolver extends VelocityLayoutViewResolver {

    private boolean exposeModelAndViewToContext = false;

    private boolean allowModelAndViewToContextOverride = false;

    private boolean exposeModelAndViewToTool = false;

    private boolean allowModelAndViewToToolOverride = false;

    @Override
    protected Class<?> requiredViewClass() {
        return EmbeddedVelocityLayoutView.class;
    }

    @Override
    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        EmbeddedVelocityLayoutView view = (EmbeddedVelocityLayoutView) super.buildView(viewName);
        view.setExposeModelAndViewToContext(this.isExposeModelAndViewToContext());
        view.setAllowModelAndViewToContextOverride(this.isAllowModelAndViewToContextOverride());
        view.setExposeModelAndViewToTool(this.isExposeModelAndViewToTool());
        view.setAllowModelAndViewToToolOverride(this.isAllowModelAndViewToToolOverride());
        return view;
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
}
