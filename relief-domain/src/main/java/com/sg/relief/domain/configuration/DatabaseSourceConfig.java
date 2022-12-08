package com.sg.relief.domain.configuration;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.sg.relief.domain"})
public class DatabaseSourceConfig {

    @Primary
    @Bean(name="dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource autoDatasource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public DataSource dataSource(){
        DataSource dataSource = new HikariDataSource();
        return dataSource;
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory (
            EntityManagerFactoryBuilder builder, @Qualifier("dataSource")DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.sg.relief.domain")
                .persistenceUnit("hikari")
                .properties(jpaProperties())
                .build();
    }

    protected Map<String, Object> jpaProperties(){
        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
        props.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
        return props;
    }

    // cctv upload 시 사용
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory2(
            DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
        lef.setDataSource(dataSource);
        lef.setJpaVendorAdapter(jpaVendorAdapter);
        lef.setPackagesToScan("com.sg.relief.domain");
        return lef;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setGenerateDdl(true); //Auto creating scheme when true
        hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);//Database type
        return hibernateJpaVendorAdapter;
    }
}
