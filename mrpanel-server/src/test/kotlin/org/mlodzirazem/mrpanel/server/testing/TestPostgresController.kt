package org.mlodzirazem.mrpanel.server.testing

import org.flywaydb.core.Flyway
import org.slf4j.LoggerFactory
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.lifecycle.Startables

data class JdbcProperties(val url: String, val username: String, val password: String)

object TestPostgresController {
    private val LOG = LoggerFactory.getLogger(TestPostgresController::class.java)
    const val BACKUP_FILE_NAME = "schema_backup.pgsql"

    var schemaDumped = false
        private set

    private val postgresContainer = PostgreSQLContainer<Nothing>("postgres:17.5")

    val jdbcProperties
        get() = if (postgresContainer.isRunning) JdbcProperties(
            postgresContainer.jdbcUrl,
            postgresContainer.username,
            postgresContainer.password
        ) else throw IllegalStateException("PostgreSQL container is not running.")

    @Synchronized
    fun startContainer() {
        if (!postgresContainer.isRunning) {
            Startables.deepStart(postgresContainer).join()
        }
    }

    @Synchronized
    fun restoreSchema() {
        if (!schemaDumped) {
            LOG.info("No schema dump found. Skipping schema restore.")
            return
        }

        LOG.info("Truncating schema")
        postgresContainer.execInContainer("psql", "-U", postgresContainer.username, "-c", "DROP SCHEMA public CASCADE; CREATE SCHEMA public;").apply {
            if (exitCode != 0) {
                throw IllegalStateException("Failed to truncate schema. Exit code: ${exitCode}.\nstdout:\n${stdout}\nstderr:\n${stderr}")
            }

            LOG.info("Truncated schema.\nstdout:\n{}\nstderr:{}", stdout, stderr)
        }


        LOG.info("Restoring schema from backup file: $BACKUP_FILE_NAME")
        postgresContainer.execInContainer(
            "psql",
            postgresContainer.databaseName,
            "-U",
            postgresContainer.username,
            "-f",
            BACKUP_FILE_NAME
        ).let {
            LOG.info("Restored schema from backup.\nstdout:\n{}\nstderr:{}", it.stdout, it.stderr)

            if (it.exitCode != 0) {
                throw IllegalStateException("Failed to restore schema from backup. Exit code: ${it.exitCode}.")
            }
        }
    }

    fun backupSchema() {
        LOG.info("Dumping migrated schema to file: $BACKUP_FILE_NAME")
        postgresContainer.execInContainer(
            "pg_dump",
            postgresContainer.databaseName,
            "-U",
            postgresContainer.username,
            "-f",
            BACKUP_FILE_NAME
        ).let {
            LOG.info("Dumped public schema.\nstdout:\n{}\nstderr:{}", it.stdout, it.stderr)

            if (it.exitCode != 0) {
                throw IllegalStateException("Failed to dump schema. Exit code: ${it.exitCode}.")
            }
        }

        schemaDumped = true
    }
}