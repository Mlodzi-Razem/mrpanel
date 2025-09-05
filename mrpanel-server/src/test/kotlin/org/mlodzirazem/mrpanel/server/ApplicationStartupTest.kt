package org.mlodzirazem.mrpanel.server

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.beNull
import io.kotest.matchers.shouldNot
import org.mlodzirazem.mrpanel.server.Profiles.GOOGLE_MOCK
import org.mlodzirazem.mrpanel.server.testing.EnableTestPostgresql
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext

@SpringBootTest
@EnableTestPostgresql
class ApplicationStartupTest(private val applicationContext: ApplicationContext) : FunSpec({
    test("context loads") {
        applicationContext shouldNot beNull()
    }
})