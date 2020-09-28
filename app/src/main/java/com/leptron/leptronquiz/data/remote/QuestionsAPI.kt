package com.leptron.leptronquiz.data.remote

import com.leptron.leptronquiz.data.remote.apiobjects.QuestionResultData
import retrofit2.http.GET
import retrofit2.http.Headers

interface QuestionsAPI {

    @Headers("Accept: application/json", "Content-Type: application/json")
    @GET("/api.php?amount=4&type=boolean")
    suspend fun getQuestions(): QuestionResultData
}