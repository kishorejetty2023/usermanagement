package com.java.user.management.configuration;


import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "userEntityManagerFactory",
        basePackages = {"com.java.user.management.repository"},
        transactionManagerRef = "userTransactionManager")
public class DBConfig {

    @Value("${spring.user.datasource.hibernate.ddl-auto}")
    private String ddlAuto;

    @Value("${spring.user.datasource.hibernate.show-sql}")
    private String showSql;

    @Value("${spring.user.datasource.hibernate.format-sql}")
    private String formatSql;

    @Value("${spring.user.datasource.hibernate.dialect}")
    private String dialect;

    @Primary
    @Bean(name = "userDataSource")
    @ConfigurationProperties(prefix = "spring.user.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "userEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean
            (EntityManagerFactoryBuilder builder, @Qualifier("userDataSource") DataSource dataSource) {
        Map<String, Object> dbPropertes = getDBProperties();
        return builder.dataSource(dataSource)
                .packages("com.java.user.management.entity")
                .properties(dbPropertes)
                .persistenceUnit("UserEntity").build();
    }

    @Primary
    @Bean(name = "userTransactionManager")
    public PlatformTransactionManager transactionManager
            (@Qualifier("userEntityManagerFactory") EntityManagerFactory userEntityManagerFactory) {
        return new JpaTransactionManager(userEntityManagerFactory);
    }
    private Map<String, Object> getDBProperties() {
        // Hibernate settings
        Map<String,Object> propertes = new HashMap<>();
        propertes.put("hibernate.hbm2ddl.auto",ddlAuto);
        propertes.put("hibernate.show_sql",showSql);
        propertes.put("hibernate.format_sql",formatSql);
        propertes.put("hibernate.dialect",dialect);
        return propertes;
    }

}
