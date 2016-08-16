package br.frico.bagarita.config;

import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Configuration for database access
 *
 * Created by Felipe Rico on 8/16/2016.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "br.frico.bagarita.domain.repos")
public class DBConfig {

    @Autowired
    private Environment environment;

    @Value("${database.username}")
    private String username;

    @Value("${database.password}")
    private String password;

    @Value("${database.servername}")
    private String serverName;

    @Value("${database.portnumber}")
    private int portNumber;

    @Value("${database.name}")
    private String databaseName;

    @Bean
    @Profile("dev")
    public DataSource devDataSource()  {
        PGPoolingDataSource dataSource = new PGPoolingDataSource();
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setServerName(serverName);
        dataSource.setPortNumber(portNumber);
        dataSource.setDatabaseName(databaseName);
        return dataSource;
    }

    @Bean
    @Profile("!dev")
    public DataSource prodDataSource() {
        JndiDataSourceLookup dataSource = new JndiDataSourceLookup();
        dataSource.setResourceRef(true);
        return dataSource.getDataSource("java:/Database");
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setShowSql(true);

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
        jpaProperties.put("hibernate.dialect"     , environment.getProperty("hibernate.dialect"));
        jpaProperties.put("hibernate.show_sql"    , environment.getProperty("hibernate.show_sql"));

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource());
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setJpaProperties(jpaProperties);
        factory.setPackagesToScan("br.frico.bagarita.domain");
//        factory.setPersistenceXmlLocation("classpath:META-INF/persistence.xml");
        factory.setPersistenceUnitName("bagarita");

        return factory;
    }

    private DataSource dataSource() {
        DataSource dataSource;
        if (environment.acceptsProfiles("dev")) {
            dataSource = devDataSource();
        } else {
            dataSource = prodDataSource();
        }
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        EntityManagerFactory factory = entityManagerFactory().getObject();
        return new JpaTransactionManager(factory);
    }


}
