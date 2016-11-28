package org.libre.lingvo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by igorek2312 on 07.09.16.
 */

@Configuration
@ComponentScan
@EnableTransactionManagement
public class DatabaseConfig {
    @Value("${hibernate.dialect}")
    private String HIBERNATE_DIALECT;

    @Value("${hibernate.show_sql}")
    private String HIBERNATE_SHOW_SQL;

    @Value("${hibernate.format_sql}")
    private String HIBERNATE_FORMAT_SQL;

    @Value("${hibernate.hbm2ddl.auto}")
    private String HIBERNATE_HBM2DDL_AUTO;

    @Value("${hibernate.hbm2ddl.import_files}")
    private String HIBERNATE_HBM2DDL_IMPORT_FILES;

    @Value("${hibernate.hbm2ddl.import_files_sql_extractor}")
    private String HIBERNATE_HBM2DDL_IMPORT_FILES_SQL_EXTRACTOR;

    @Value("${hibernate.entitymanager.packagesToScan}")
    private String ENTITYMANAGER_PACKAGES_TO_SCAN;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private LocalContainerEntityManagerFactoryBean entityManagerFactory;

    /**
     * Declare the JPA entities manager factory.
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactory.setDataSource(dataSource);

        // Classpath scanning of @Component, @Service, etc annotated class
        entityManagerFactory.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);

        // Vendor adapter
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);

        // Hibernate properties
        Properties additionalProperties = new Properties();
        additionalProperties.put("hibernate.dialect", HIBERNATE_DIALECT);
        additionalProperties.put("hibernate.show_sql", HIBERNATE_SHOW_SQL);
        additionalProperties.put("hibernate.format_sql", HIBERNATE_FORMAT_SQL);
        additionalProperties.put("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL_AUTO);
        additionalProperties.put("hibernate.hbm2ddl.import_files", HIBERNATE_HBM2DDL_IMPORT_FILES);
        additionalProperties.put("hibernate.hbm2ddl.import_files_sql_extractor", HIBERNATE_HBM2DDL_IMPORT_FILES_SQL_EXTRACTOR);

        additionalProperties.put("hibernate.connection.CharSet", "utf8");
        additionalProperties.put("hibernate.connection.characterEncoding", "utf8");
        additionalProperties.put("hibernate.connection.useUnicode", "true");

        entityManagerFactory.setJpaProperties(additionalProperties);

        return entityManagerFactory;
    }

    /**
     * Declare the transaction manager.
     */
    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
        return transactionManager;
    }

    /**
     * PersistenceExceptionTranslationPostProcessor is a bean post processor
     * which adds an advisor to any bean annotated with Repository so that any
     * platform-specific exceptions are caught and then rethrown as one
     * Spring's unchecked data access exceptions (i.e. a subclass of
     * DataAccessException).
     */
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

}