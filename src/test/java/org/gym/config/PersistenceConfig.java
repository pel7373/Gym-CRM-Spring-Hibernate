package org.gym.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.SharedCacheMode;
import jakarta.persistence.ValidationMode;
import jakarta.persistence.spi.ClassTransformer;
import jakarta.persistence.spi.PersistenceUnitInfo;
import jakarta.persistence.spi.PersistenceUnitTransactionType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.net.URL;
import java.util.List;
import java.util.Properties;


@Configuration
//@EnableJpaRepositories
@TestPropertySource(locations = "classpath:application-test.properties")
@ComponentScan(basePackages = "org.gym")
public class PersistenceConfig implements PersistenceUnitInfo {

    @Value("${test.datasource.url}")
    private String datasourceUrl;

    @Value("${test.datasource.username}")
    private String datasourceUsername;

    @Value("${test.datasource.password}")
    private String datasourcePassword;

    @Value("${test.hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;

    @Value("${test.hibernate.dialect}")
    private String hibernateDialect;

    @Value("${test.hibernate.show_sql}")
    private String showSql;

    @Value("${test.hibernate.format_sql}")
    private String formatSql;

    @Value("${test.hibernate.jdbc.lob.non_contextual_creation}")
    private String lobCreation;

    @Value("${test.jakarta.persistence.sql-load-script-source}")
    private String loadScriptSource;

    @Value("${test.spring.jpa.defer-datasource-initialization}")
    private String jpaDeferDatasourceInitialization;

    @Value("${test.spring.sql.init.platform}")
    private String sqlInitPlatform;

    @Value("${test.spring.sql.init.mode}")
    private String sqlInitMode;

    @Value("${test.hibernate.hbm2ddl.import_files}")
    private String hbm2ddlImport_files;

    @Override
    public DataSource getNonJtaDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(datasourceUrl);
        dataSource.setUsername(datasourceUsername);
        dataSource.setPassword(datasourcePassword);
        return dataSource;
    }

    @Override
    public Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", hbm2ddlAuto);
        properties.setProperty("hibernate.dialect", hibernateDialect);
        properties.setProperty("hibernate.show_sql", showSql);
        properties.setProperty("hibernate.format_sql", formatSql);
        properties.setProperty("hibernate.jdbc.lob.non_contextual_creation", lobCreation);
        properties.setProperty("spring.jpa.defer-datasource-initialization", jpaDeferDatasourceInitialization);
        properties.setProperty("spring.sql.init.platform", sqlInitPlatform);
        properties.setProperty("spring.sql.init.mode", sqlInitMode);
        properties.setProperty("hibernate.hbm2ddl.import_files", hbm2ddlImport_files);
        properties.setProperty("jakarta.persistence.sql-load-script-source", loadScriptSource);
        return properties;
    }

    @Override
    public String getPersistenceProviderClassName() {
        return "org.hibernate.jpa.HibernatePersistenceProvider";
    }

    @Override
    public List<String> getManagedClassNames() {
        return List.of("org.gym.entity");
    }

    @Override
    public PersistenceUnitTransactionType getTransactionType() {
        return PersistenceUnitTransactionType.RESOURCE_LOCAL;
    }

    @Override
    public DataSource getJtaDataSource() {
        return null;
    }

    @Override
    public String getPersistenceUnitName() {
        return "default";
    }

    @Override
    public List<String> getMappingFileNames() {
        return List.of();
    }

    @Override
    public List<URL> getJarFileUrls() {
        return List.of();
    }

    @Override
    public URL getPersistenceUnitRootUrl() {
        return null;
    }

    @Override
    public boolean excludeUnlistedClasses() {
        return false;
    }

    @Override
    public SharedCacheMode getSharedCacheMode() {
        return SharedCacheMode.ENABLE_SELECTIVE;
    }

    @Override
    public ValidationMode getValidationMode() {
        return ValidationMode.AUTO;
    }

    @Override
    public String getPersistenceXMLSchemaVersion() {
        return "3.0";
    }

    @Override
    public ClassLoader getClassLoader() {
        return getClass().getClassLoader();
    }

    @Override
    public void addTransformer(ClassTransformer classTransformer) {
        // nothing to do
    }

    @Override
    public ClassLoader getNewTempClassLoader() {
        return null;
    }
}
