package org.mlodzirazem.mrpanel.server.testing

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext

/**
 * Initializes a PostgreSQL test container and configures the application context
 * with the corresponding database connection properties.
 *
 * This class is used as an `ApplicationContextInitializer` to integrate a PostgreSQL
 * container during the test lifecycle. It leverages `TestPostgresController` to manage
 * the container and applies the required JDBC connection properties (URL, username, password)
 * to the Spring environment.
 *
 * Key functionalities:
 * - Starts the PostgreSQL container if not already running.
 * - Retrieves JDBC connection properties from the running container.
 * - Applies these properties to the application context for database connection during tests.
 *
 * This ensures an isolated, consistent database environment for integration tests.
 *
 * Use only via [EnableTestPostgresql].
 */
class TestPostgresInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        TestPostgresController.startContainer()

        TestPropertyValues.of(
            TestPostgresController.jdbcProperties.run {
                mapOf(
                    "spring.datasource.url" to url,
                    "spring.datasource.username" to username,
                    "spring.datasource.password" to password
                )
            }
        ).applyTo(applicationContext.environment);
    }
}