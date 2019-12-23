package com.alibaba.boot.velocity.autoconfigure;

import com.alibaba.boot.velocity.VelocityLayoutProperties;
import com.alibaba.boot.velocity.autoconfigure.condition.VelocityLayoutCondition;
import com.alibaba.boot.velocity.web.servlet.VelocityLayoutHandlerInterceptor;
import com.alibaba.boot.velocity.web.servlet.view.EmbeddedVelocityLayoutViewResolver;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolManager;
import org.apache.velocity.tools.Toolbox;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.autoconfigure.velocity.VelocityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.ui.velocity.VelocityEngineFactory;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

import javax.servlet.Servlet;

import static com.alibaba.boot.velocity.VelocityConstants.VELOCITY_VIEW_RESOLVER_BEAN_NAME;

/**
 * {@link VelocityLayoutAutoConfiguration}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @version 1.0.0
 * @see VelocityLayoutAutoConfiguration
 * @since 1.0.0 2016-07-17
 */
@Configuration
@ConditionalOnClass({VelocityEngine.class, VelocityEngineFactory.class})
@Conditional(VelocityLayoutCondition.class)
@AutoConfigureAfter(
        name = {
                "org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration", //Compatible with Spring Boot 1.x
                "org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration"
        }
)
@AutoConfigureBefore(VelocityAutoConfiguration.class)
public class VelocityLayoutAutoConfiguration {

    @Configuration
    @ConditionalOnClass({Servlet.class, VelocityConfigurer.class})
    @ConditionalOnWebApplication
    @Conditional(VelocityLayoutCondition.class)
    public static class VelocityLayoutWebConfiguration {

        @Primary
        @Bean
        @ConditionalOnMissingBean(VelocityLayoutProperties.class)
        public VelocityLayoutProperties velocityLayoutProperties() {
            return new VelocityLayoutProperties();
        }

        @Bean(name = VELOCITY_VIEW_RESOLVER_BEAN_NAME)
        @ConditionalOnMissingBean(value = VelocityViewResolver.class)
        public EmbeddedVelocityLayoutViewResolver embeddedVelocityLayoutViewResolver(
                VelocityLayoutProperties velocityLayoutProperties) {
            EmbeddedVelocityLayoutViewResolver resolver = new EmbeddedVelocityLayoutViewResolver();
            velocityLayoutProperties.applyToViewResolver(resolver);
            return resolver;
        }

        @Bean
        public VelocityLayoutWebMvcConfigurer velocityLayoutWebMvcConfigurer(
                VelocityLayoutProperties velocityLayoutProperties) {
            return new VelocityLayoutWebMvcConfigurer(velocityLayoutProperties);
        }

        @Bean
        @ConditionalOnProperty("spring.velocity.toolbox-config-location")
        public ToolManager toolManager(
                VelocityEngine velocityEngine,
                VelocityLayoutProperties velocityLayoutProperties) {
            ToolManager toolManager = new ToolManager();
            toolManager.setVelocityEngine(velocityEngine);
            toolManager.configure(velocityLayoutProperties.getToolboxConfigLocation());
            return toolManager;
        }

        /**
         * application is static, so define is singleton.
         */
        @Bean
        @ConditionalOnBean(ToolManager.class)
        public Toolbox applicationToolbox(
                ToolManager toolManager) {
            Toolbox toolbox = toolManager.getToolboxFactory().createToolbox(
                    Scope.APPLICATION);
            return toolbox;
        }
    }


    /**
     * {@link Velocity} {@link WebMvcConfigurer} implementation
     */
    final static class VelocityLayoutWebMvcConfigurer extends WebMvcConfigurerAdapter {

        private final VelocityLayoutProperties velocityLayoutProperties;

        private VelocityLayoutWebMvcConfigurer(VelocityLayoutProperties velocityLayoutProperties) {
            this.velocityLayoutProperties = velocityLayoutProperties;
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {

            VelocityLayoutHandlerInterceptor velocityLayoutHandlerInterceptor =
                    new VelocityLayoutHandlerInterceptor(velocityLayoutProperties.getLayoutKey());

            registry.addInterceptor(velocityLayoutHandlerInterceptor);

        }

    }

}
