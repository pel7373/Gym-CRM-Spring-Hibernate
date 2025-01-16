package org.gym.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class JpaConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(org.gym.config.PersistenceConfig persistenceConfig) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(persistenceConfig.getNonJtaDataSource());
        em.setPackagesToScan("org.gym.entity");
        em.setPersistenceProviderClass(org.hibernate.jpa.HibernatePersistenceProvider.class);
        em.setJpaProperties(persistenceConfig.getProperties());
        return em;
    }


    @Bean
    @DependsOn("entityManagerFactory")
    public EntityManager entityManager(LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean) {
        EntityManagerFactory entityManagerFactory = localContainerEntityManagerFactoryBean.getObject();
        if (entityManagerFactory == null) {
            throw new IllegalStateException("EntityManagerFactory is not initialized");
        }
        return entityManagerFactory.createEntityManager();
    }

    @Bean
    @DependsOn("entityManagerFactory")
    public TransactionManager transactionManager(LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean) {
        EntityManagerFactory entityManagerFactory = localContainerEntityManagerFactoryBean.getObject();
        if (entityManagerFactory == null) {
            throw new IllegalStateException("EntityManagerFactory is not initialized");
        }
        return new JpaTransactionManager(entityManagerFactory);
    }
}
