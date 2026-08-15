package com.heroku.java.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

/**
 * Serves user-uploaded files from a real filesystem directory instead of the
 * classpath. Runtime uploads are written to {@code ${user.dir}/uploads}, which
 * is NOT inside the packaged jar, so Spring's default classpath static serving
 * would never find them (images would 404 and only the alt text would show).
 * Bundled images (barber-images, favicon) still resolve via the classpath
 * fallback location.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${barbershop.upload-dir:${user.dir}/uploads}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String externalLocation = Paths.get(uploadDir).toUri().toString();
        registry.addResourceHandler("/resources/uploads/**")
                .addResourceLocations(externalLocation, "classpath:/static/resources/uploads/");
    }
}