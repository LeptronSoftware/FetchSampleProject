package com.leptron.leptronquiz.data.remote

interface QuestionsRemoteData {
    suspend fun  refreshQuestions()
}