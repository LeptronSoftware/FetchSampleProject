package com.leptron.leptronquiz.data

import androidx.lifecycle.LiveData
import com.leptron.leptronquiz.data.local.QuestionHistory
import com.leptron.leptronquiz.data.remote.ResultWrapper

interface QuestionsLocalData {
    fun observeQuestions(): LiveData<List<QuestionHistory>>
    suspend fun refreshQuestions()
    suspend fun getQuestions(): ResultWrapper<List<QuestionHistory>>
    suspend fun saveQuestion(questionHistory: QuestionHistory)
    suspend fun deleteAllTasks()
    suspend fun updateQuestionAnswer(questionHistory: QuestionHistory)
}