package com.heroku.java.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Serves the bundled image assets (barber-images, favicon, logo) from the
 * classpath. User-uploaded profile pictures are stored in the database and
 * served by {@link com.heroku.java.controller.ImageController} so they survive
 * Heroku's ephemeral dyno filesystem across deploys/restarts.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/uploads/**")
                .addResourceLocations("classpath:/static/resources/uploads/");
    }
}