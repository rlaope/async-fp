package rlaope

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.coroutines.channels.Channel

val logChannel = Channel<LogRequest>(capacity = 10_000)

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}

fun Application.module() {
    configureSerialization()
    install(ContentNegotiation) {
        json()
    }
    configureMonitoring()
    configureRouting()
}
