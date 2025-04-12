package rlaope

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.channels.Channel

val logChannel = Channel<LogRequest>(capacity = 10_000)

fun main(args: Array<String>) {
    DatabaseFactory.init()
    embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}

fun Application.module() {

    configureRouting()

    AsyncProcessor.start(logChannel)
}
