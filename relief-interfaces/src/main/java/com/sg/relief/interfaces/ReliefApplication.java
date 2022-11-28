package com.sg.relief.interfaces;

//import lombok.extern.slf4j.Slf4j;
import com.sg.relief.domain.ReliefDomainApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;


@PropertySource("classpath:application-interfaces.properties")
@Slf4j
@EnableCaching
@ServletComponentScan
@SpringBootApplication
public class ReliefApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(ReliefDomainApplication.class, ReliefApplication.class)
                .run(args);
    }
}
