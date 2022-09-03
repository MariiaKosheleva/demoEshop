package com.example.demoeshop.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class DataSourceConfig {
    @Bean
    public DataSource postgresDataSource() {
        String dbUrl = System.getenv("DATABASE_URL");

        String url = "jdbc:postgresql://localhost:5432/demoEShop";
        String user = "postgres";
        String pass = "mama1101";

        if (dbUrl != null) {
            try {
                URI dbUri = new URI(dbUrl);
                url = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";
                user = dbUri.getUserInfo().split(":")[0];
                pass = dbUri.getUserInfo().split(":")[1];
            } catch (URISyntaxException | NullPointerException e) {
                return null;
            }
        }

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(user);
        dataSourceBuilder.password(pass);
        return dataSourceBuilder.build();
    }
}
