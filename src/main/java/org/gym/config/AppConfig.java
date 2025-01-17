package org.gym.config;

import org.springframework.context.annotation.*;

import java.security.SecureRandom;

@Configuration
public class AppConfig {
    public static final String ID_CANT_BE_NULL_OR_NEGATIVE = "%s: id can't be null or negative";
    public static final String ENTITY_CANT_BE_NULL = "%s: entity can't be null";

    @Bean
    public SecureRandom secureRandom() {
        return new SecureRandom();
    }

}
