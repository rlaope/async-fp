package rlaope

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object AsyncProcessor {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val logBatch = mutableListOf<LogRequest>()
    private val flushIntervalMillis = 3000L
    private val flushThreshold = 100

    fun start(channel: ReceiveChannel<LogRequest>) {
        scope.launch {
            val start = System.currentTimeMillis()

            while(System.currentTimeMillis() - start < flushIntervalMillis && logBatch.size < flushThreshold) {
                val log = withTimeoutOrNull(1000) { channel.receive() }
                log?.let { logBatch.add(it) }
            }

            if(logBatch.isNotEmpty()) {
                flushToFile()
            }
        }
    }

    private fun flushToFile() {
        val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"))
        val file = File("logs/log-$timestamp.txt").apply { parentFile.mkdir() }

        file.appendText(
            logBatch.joinToString("\n") {
                "[${it.timestamp}] [${it.level}] ${it.service}: ${it.message} (${it.meta})"
            } + "\n"
        )

        println("Flushed ${logBatch.size} logs to $file")
        logBatch.clear()
    }
}