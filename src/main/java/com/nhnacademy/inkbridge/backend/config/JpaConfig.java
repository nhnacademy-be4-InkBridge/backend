package com.nhnacademy.inkbridge.backend.config;

import com.nhnacademy.inkbridge.backend.property.JpaProperty;
import javax.sql.DataSource;
import lombok.AllArgsConstructor;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * class: JpaConfig.
 *
 * @author jangjaehun
 * @version 2024/02/23
 */
@Configuration
@AllArgsConstructor
public class JpaConfig {

    private JpaProperty jpaProperty;

    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUrl(jpaProperty.getUrl());
        dataSource.setUsername(jpaProperty.getUsername());
        dataSource.setPassword(jpaProperty.getPassword());

        dataSource.setInitialSize(20);
        dataSource.setMaxIdle(20);
        dataSource.setMinIdle(20);
        dataSource.setMaxTotal(20);

        dataSource.setMaxWaitMillis(20);

        dataSource.setTestOnBorrow(true);
        dataSource.setTestOnReturn(true);
        dataSource.setTestWhileIdle(true);

        return dataSource;
    }
}
