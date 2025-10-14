package org.mlodzirazem.mrpanel.server

import io.kotest.core.extensions.ApplyExtension
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.nulls.beNull
import io.kotest.matchers.shouldNot
import org.mlodzirazem.mrpanel.server.testing.EnableTestPostgresql
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext

@ApplyExtension(SpringExtension::class)
@SpringBootTest
@EnableTestPostgresql
class ApplicationStartupTest(private val applicationContext: ApplicationContext) : FunSpec({
    test("context loads") {
        applicationContext shouldNot beNull()
    }
})