package com.leptron.leptronquiz

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.leptron.leptronquiz.data.local.QuestionsDatabase
import com.leptron.leptronquiz.data.local.QuestionsLocalDataSource
import com.leptron.leptronquiz.data.remote.QuestionsRemoteRepository

object ServiceLocator {
    private val lock = Any()
    private var database: QuestionsDatabase? = null
    @Volatile
    var questionsRemoteRepository: QuestionsRemoteRepository? = null
        @VisibleForTesting set
    @Volatile
    var questionsLocalRepository: QuestionsLocalDataSource? = null
        @VisibleForTesting set

    fun provideQuestionsLocalDataSource(context: Context): QuestionsLocalDataSource {
        synchronized(this) {
            return questionsLocalRepository ?: questionsLocalRepository ?: createTaskLocalDataSource(context)
        }
    }
    fun provideQuestionsRemoteDataSource(context: Context): QuestionsRemoteRepository {
        synchronized(this) {
            return questionsRemoteRepository ?: questionsRemoteRepository ?: createTasksRepository(context)
        }
    }

    private fun createTasksRepository(context: Context): QuestionsRemoteRepository {

        val newRepo = QuestionsRemoteRepository( createTaskLocalDataSource(context))
        questionsRemoteRepository = newRepo
        return newRepo
    }

    private fun createTaskLocalDataSource(context: Context): QuestionsLocalDataSource {
        val database = database ?: createDataBase(context)
        return QuestionsLocalDataSource(database.questionHistoryDao())
    }

    private fun createDataBase(context: Context): QuestionsDatabase {
        val result = Room.databaseBuilder(
            context.applicationContext,
            QuestionsDatabase::class.java, "questions.db"
        ).build()
        database = result
        return result
    }

    @VisibleForTesting
    fun resetRepository() {
        synchronized(lock) {

            // Clear all data to avoid test pollution.
            database?.apply {
                clearAllTables()
                close()
            }
            database = null
            questionsRemoteRepository = null
            questionsLocalRepository = null
        }
    }
}