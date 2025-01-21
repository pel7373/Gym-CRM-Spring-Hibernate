package org.gym;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@Slf4j
@ComponentScan(basePackages = "org.gym")
@PropertySource("classpath:application.properties")
public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        System.setProperty("spring.profiles.active", "prod");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
    }
}
