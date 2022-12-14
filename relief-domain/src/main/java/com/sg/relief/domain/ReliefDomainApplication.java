package com.sg.relief.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

@PropertySource("classpath:application-domain.properties")
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableAsync
public class ReliefDomainApplication {
}
