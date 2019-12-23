package com.alibaba.boot.velocity.web.servlet.view;

import org.springframework.boot.web.servlet.view.velocity.EmbeddedVelocityViewResolver;
import org.springframework.web.servlet.view.velocity.VelocityLayoutView;
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

    private String toolboxConfigLocation;

    @Override
    protected void initApplicationContext() {
        if (this.toolboxConfigLocation != null) {
            if (VelocityLayoutView.class.equals(getViewClass())) {
                this.logger.info("Using EmbeddedVelocityLayoutToolboxView instead of "
                        + "default VelocityView due to specified toolboxConfigLocation");
                setViewClass(EmbeddedVelocityLayoutView.class);
            }
        }
        super.initApplicationContext();
    }

    @Override
    public void setToolboxConfigLocation(String toolboxConfigLocation) {
        super.setToolboxConfigLocation(toolboxConfigLocation);
        this.toolboxConfigLocation = toolboxConfigLocation;
    }
}
