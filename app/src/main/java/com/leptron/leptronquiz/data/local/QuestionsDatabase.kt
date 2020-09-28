package com.leptron.leptronquiz.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/*
* The Room Database that contains the Questions table.
 */
@Database(entities = [QuestionHistory::class], version = 1, exportSchema = false)
abstract class QuestionsDatabase : RoomDatabase() {
    abstract fun questionHistoryDao(): QuestionHistoryDao
}