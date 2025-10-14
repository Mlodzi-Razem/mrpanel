package org.mlodzirazem.mrpanel.server.testing

import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.boot.flyway.autoconfigure.FlywayMigrationInitializer
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext

class TestPostgresInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        applicationContext.beanFactory.registerSingleton("schemaBackupFlywayPostprocessor", SchemaBackupFlywayPostprocessor())

        TestPostgresController.startContainer()

        TestPropertyValues.of(mapOf(
            "spring.flyway.url" to TestPostgresController.jdbcProperties.url,
            "spring.flyway.user" to TestPostgresController.jdbcProperties.username,
            "spring.flyway.password" to TestPostgresController.jdbcProperties.password,
            "spring.flyway.enabled" to "true",
            "spring.datasource.url" to TestPostgresController.jdbcProperties.url,
            "spring.datasource.username" to TestPostgresController.jdbcProperties.username,
            "spring.datasource.password" to TestPostgresController.jdbcProperties.password,
        )).applyTo(applicationContext)
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