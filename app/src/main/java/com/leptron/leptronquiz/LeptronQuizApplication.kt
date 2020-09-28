package com.leptron.leptronquiz

import android.app.Application
import com.leptron.leptronquiz.data.local.QuestionsLocalDataSource
import com.leptron.leptronquiz.data.remote.QuestionsRemoteRepository
import timber.log.Timber

class LeptronQuizApplication : Application() {
    // Depends on the flavor,
    val questionsLocalRepository: QuestionsLocalDataSource
        get() = ServiceLocator.provideQuestionsLocalDataSource(this)

    val questionsRemoteRepository: QuestionsRemoteRepository
        get() = ServiceLocator.provideQuestionsRemoteDataSource(this)

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}