package org.libre.lingvo.config;

import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

/**
 * Created by igorek2312 on 28.11.16.
 */

@Configuration
@Profile("cloud")
public class CloudDataSourceConfig extends AbstractCloudConfig {
    @Bean
    public DataSource dataSource() {
        DataSource dataSource = connectionFactory().dataSource();
        if (dataSource instanceof org.apache.tomcat.jdbc.pool.DataSource) {
            org.apache.tomcat.jdbc.pool.DataSource tomcatDatasource =
                    (org.apache.tomcat.jdbc.pool.DataSource) dataSource;
            tomcatDatasource.setConnectionProperties("useUnicode=yes;characterEncoding=utf8;");
        }
        return dataSource;
    }
}