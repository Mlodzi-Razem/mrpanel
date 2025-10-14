package org.mlodzirazem.mrpanel.server.testing

import org.springframework.test.context.ContextConfiguration

/**
 * Annotation to enable the configuration of a PostgreSQL test container for integration tests.
 *
 * When applied to a test class, it initializes a PostgreSQL test container using the
 * `TestPostgresInitializer` and sets the necessary database properties (e.g., URL, username,
 * password) to the application context environment.
 *
 * This facilitates the execution of tests with a runtime PostgreSQL database that is isolated and
 * managed independently for the test lifecycle.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@ContextConfiguration(initializers = [TestPostgresInitializer::class])
annotation class EnableTestPostgresql()
