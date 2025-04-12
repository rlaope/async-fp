package rlaope

import java.time.LocalDateTime

data class FlushRecord(
    val fileName: String,
    val fileCount: Int,
    val flushDuration: Long,
    val timestamp: LocalDateTime

)
