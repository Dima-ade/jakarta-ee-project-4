package org.example.beans;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class DataSourceConfiguration {

    private final EntityManagerFactory entityManagerFactory;

    public DataSourceConfiguration() {
        DataSource dataSource = buildDataSource();
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("jakarta.persistence.jtaDataSource", dataSource);
        this.entityManagerFactory = Persistence.createEntityManagerFactory("bookstore-PU", props);
    }

    public EntityManager createEntityManager() {
        return this.entityManagerFactory.createEntityManager();
    }


    private DataSource buildDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/bookstore");
        config.setUsername("postgres");
        config.setPassword("postgres");
        config.setDriverClassName("org.postgresql.Driver");

        return new HikariDataSource(config); // HikariCP 5.x (uses jakarta.sql.DataSource)
    }
}
