package com.alibaba.boot.velocity;

import org.apache.velocity.app.Velocity;
import org.springframework.boot.autoconfigure.velocity.VelocityAutoConfiguration;
import org.springframework.boot.autoconfigure.velocity.VelocityProperties;

/**
 * {@link VelocityConstants}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @see VelocityConstants
 * @since 1.0.1 2016-11-03
 */
public interface VelocityConstants {

    /**
     * The prefix of {@link VelocityProperties}
     */
    String VELOCITY_PROPERTIES_PREFIX = "spring.velocity.";

    /**
     * The property name of "enabled"
     */
    String ENABLED_PROPERTY_NAME = "enabled";

    /**
     * {@link Velocity} Auto Configuration property name
     */
    String VELOCITY_ENABLED_PROPERTY_NAME = VELOCITY_PROPERTIES_PREFIX + ENABLED_PROPERTY_NAME;

    /**
     * The property name of "layout-enabled"
     */
    String LAYOUT_ENABLED_PROPERTY_NAME = "layout-enabled";

    /**
     * {@link Velocity} Layout Auto Configuration property name
     */
    String VELOCITY_LAYOUT_ENABLED_PROPERTY_NAME = VELOCITY_PROPERTIES_PREFIX + LAYOUT_ENABLED_PROPERTY_NAME;

    /**
     * Default {@link VelocityLayoutProperties#isLayoutEnabled()} Value
     */
    boolean DEFAULT_VELOCITY_LAYOUT_ENABLED_VALUE = true;

    /**
     * The value was defined in
     * {@link VelocityAutoConfiguration.VelocityWebConfiguration#velocityViewResolver()} method.
     */
    String VELOCITY_VIEW_RESOLVER_BEAN_NAME = "velocityViewResolver";
}
