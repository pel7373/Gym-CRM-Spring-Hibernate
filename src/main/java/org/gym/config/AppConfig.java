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
    public static final String ENTITY_CANT_BE_NULL = "{}: entity can't be null";
    public static final String ACCESS_DENIED = "{}: access denied to {}";
    public static final String USERNAME_PASSWORD_CANT_BE_NULL_OR_BLANK = "{}: userName ({}) or/and password ({}) can't be null or blank";
    public static final String ENTITY_NOT_FOUND = "{}: entity not found by userName {}";
    public static final String ENTITY_NOT_FOUND_EXCEPTION = "%s: entity not found by userName %s";

    @Bean
    public SecureRandom secureRandom() {
        return new SecureRandom();
    }
}
