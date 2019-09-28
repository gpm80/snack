package ru.vkhackathon.snack;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by Petr Gusarov
 */
@EnableScheduling
@SpringBootApplication(scanBasePackages = {
    "ru.vkhackathon.snack.rest",
    "ru.vkhackathon.snack.service",
    "ru.vkhackathon.snack.repository",
    "ru.vkhackathon.snack.ektorp"
})
public class Application extends SpringBootServletInitializer {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    /**
     * Замена настроек приложения в зависимости от имени хоста
     *
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer propsConfigurer = new PropertySourcesPlaceholderConfigurer();
        propsConfigurer.setIgnoreResourceNotFound(true);
        List<Resource> resources = new ArrayList<>();
        resources.add(new ClassPathResource("default.properties"));
        try {
            resources.add(new ClassPathResource(InetAddress.getLocalHost().getHostName() + ".properties"));
        } catch (UnknownHostException e) {
            logger.error("Could not get name local host");
            throw new RuntimeException(e);
        }
        propsConfigurer.setLocations(resources.toArray(new Resource[resources.size()]));
        return propsConfigurer;
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
    }
}
