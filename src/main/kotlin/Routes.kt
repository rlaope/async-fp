package rlaope

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Health Check 200")
        }
        post("/log") {
            val logRequest = call.receive<LogRequest>()
            logChannel.send(logRequest)
            call.respond(mapOf("status" to "received"))
        }
    }
}

@Serializable
data class LogRequest(
    val timestamp: String,
    val level: String,
    val service: String,
    val message: String,
    val meta: Map<String, String> = emptyMap()
)