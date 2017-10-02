package com.babcock.user.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan("com.babcock.user.model.domain")
@EnableJpaRepositories("com.babcock.user.model.repositories")
@EnableTransactionManagement
public class DatabaseConfiguration {
}
