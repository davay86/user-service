package com.babcock.user.configuration;

import com.babcock.user.Application;
import com.babcock.user.config.CloudConfiguration;
import com.babcock.user.config.SecurityConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

@TestConfiguration
@Profile("test")
@ComponentScan(
        basePackages="com.babcock.user",
        excludeFilters = {@ComponentScan.Filter(type = ASSIGNABLE_TYPE, value = {
                Application.class,CloudConfiguration.class,SecurityConfiguration.class})
        })
public class ConfigurationForTest {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
