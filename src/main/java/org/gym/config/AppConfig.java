package org.gym.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.*;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Configuration
public class AppConfig {
    public static final String ID_CANT_BE_NULL_OR_NEGATIVE = "%s: id can't be null or negative";
    public static final String ENTITY_CANT_BE_NULL = "%s: entity can't be null";
    public static final String ACCESS_DENIED = "\\{\\}: access denied to \\{\\}";

    @Bean
    public SecureRandom secureRandom() {
        return new SecureRandom();
    }
}
