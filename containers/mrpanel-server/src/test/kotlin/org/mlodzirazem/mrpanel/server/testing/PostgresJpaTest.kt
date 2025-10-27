package org.mlodzirazem.mrpanel.server.testing

import io.kotest.core.extensions.ApplyExtension
import io.kotest.extensions.spring.SpringExtension
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase
import org.springframework.test.annotation.Rollback

@ApplyExtension(SpringExtension::class)
@DataJpaTest
@Rollback(false)
@EnableTestPostgresql
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
annotation class PostgresJpaTest()
