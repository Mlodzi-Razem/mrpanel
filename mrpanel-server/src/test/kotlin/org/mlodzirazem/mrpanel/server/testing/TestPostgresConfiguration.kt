package org.mlodzirazem.mrpanel.server.testing

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Primary
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource


@TestConfiguration
@Import(SchemaBackupFlywayPostprocessor::class)
class TestPostgresConfiguration {
    @Bean
    @Primary
    @FlywayDataSource
    fun dataSource(): DataSource {
        TestPostgresController.startContainer()

        return TestPostgresController.jdbcProperties.let { properties ->
            DataSourceBuilder.create()
                .url(properties.url)
                .username(properties.username)
                .password(properties.password)
                .driverClassName("org.postgresql.Driver")
                .build()
        }
    }
    @Bean
    fun entityManagerFactory(dataSource: DataSource): LocalContainerEntityManagerFactoryBean {
        val vendorAdapter = HibernateJpaVendorAdapter()
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect")
        vendorAdapter.setGenerateDdl(false)
        vendorAdapter.setShowSql(true)

        val factory = LocalContainerEntityManagerFactoryBean()
        factory.setJpaVendorAdapter(vendorAdapter)
        factory.setDataSource(dataSource)
        factory.setPackagesToScan("org.mlodzirazem.mrpanel.server")

        return factory
    }

    @Bean(name = ["transactionManager"])
    fun dbTransactionManager(entityManagerFactory: LocalContainerEntityManagerFactoryBean): PlatformTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = entityManagerFactory.getObject()
        return transactionManager
    }
}

/**
 * Backups the schema after Flyway is initialized
 */
class SchemaBackupFlywayPostprocessor : BeanPostProcessor {
    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        // FlywayMigrationInitializer migrates the db in afterPropertiesSet
        if (bean is FlywayMigrationInitializer) {
            if (!TestPostgresController.schemaDumped) {
                TestPostgresController.backupSchema()
            }
        }
        return bean
    }
}