package org.gym.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.text.SimpleDateFormat;
import java.util.Collections;

//@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .findAndRegisterModules()
                .registerModule(new JavaTimeModule())
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    }
//
//    @Bean
//    public RequestMappingHandlerAdapter requestMappingHandlerAdapter(ObjectMapper objectMapper) {
//        RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
//        adapter.setMessageConverters(Collections.singletonList(new MappingJackson2HttpMessageConverter(objectMapper)));
//        return adapter;
//    }
//
//    @Bean
//    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
//        return new MappingJackson2HttpMessageConverter(objectMapper);
//    }
}