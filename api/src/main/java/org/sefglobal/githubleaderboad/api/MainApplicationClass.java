package org.sefglobal.githubleaderboad.api;

import org.sefglobal.githubleaderboad.api.configuration.ConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableConfigurationProperties(ConfigProperties.class)
public class MainApplicationClass extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MainApplicationClass.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(MainApplicationClass.class, args);
    }

}
