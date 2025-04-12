package rlaope

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object FlushRecordRepository {

    fun insertFlushRecord(record: FlushRecord) {
        transaction {
            FlushRecordTable.insert {
                it[fileName] = record.fileName
                it[fileCount] = record.fileCount
                it[flushDuration] = record.flushDuration
                it[timestamp] = record.timestamp
            }
        }
    }

    fun getAllFlushRecords(): List<FlushRecord> {
        return transaction {
            FlushRecordTable.selectAll().map {
                FlushRecord(
                    fileName = it[FlushRecordTable.fileName],
                    fileCount = it[FlushRecordTable.fileCount],
                    flushDuration = it[FlushRecordTable.flushDuration],
                    timestamp = it[FlushRecordTable.timestamp]
                )
            }
        }
    }

}