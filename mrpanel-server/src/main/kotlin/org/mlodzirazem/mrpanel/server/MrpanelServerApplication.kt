package org.mlodzirazem.mrpanel.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MrpanelServerApplication

fun main(args: Array<String>) {
    runApplication<MrpanelServerApplication>(*args)
}
