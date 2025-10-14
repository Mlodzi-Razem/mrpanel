package org.mlodzirazem.mrpanel.server.testing

import io.kotest.core.extensions.ApplyExtension
import io.kotest.extensions.spring.SpringExtension
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.annotation.Rollback

@ApplyExtension(SpringExtension::class)
@DataJpaTest
@Rollback(false)
@EnableTestPostgresql
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
annotation class PostgresJpaTest()
