package org.mlodzirazem.mrpanel.libraries.directory.mock

import io.kotest.core.extensions.ApplyExtension
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext

@SpringBootTest(classes = [DirectoryMockAutoConfiguration::class], properties = ["mrpanel.directory.provider=mock"])
@ApplyExtension(SpringExtension::class)
class DirectoryMockContextLoads(private val context: ApplicationContext) : FunSpec({
    test("context loads") {
        context shouldNotBe null
    }
})