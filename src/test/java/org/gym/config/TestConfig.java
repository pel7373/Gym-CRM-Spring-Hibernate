package org.gym.config;

import org.gym.Main;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@Configuration
@TestPropertySource(locations = "classpath:application-test.properties")
@ComponentScan(basePackages = "org.gym")
public class TestConfig {

}
