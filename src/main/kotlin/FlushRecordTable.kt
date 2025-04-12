package rlaope

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object FlushRecordTable : Table("log_flush_record") {
    val id = integer("id").autoIncrement()
    val fileName = varchar("file_name", 255)
    val fileCount = integer("file_count")
    val flushDuration = long("flush_duration")
    val timestamp = datetime("timestamp")

    override val primaryKey = PrimaryKey(id, name = "PK_LogFlushRecords_ID")
}