package com.leptron.leptronquiz.data.local

import androidx.lifecycle.LiveData
import com.leptron.leptronquiz.data.DataError
import com.leptron.leptronquiz.data.QuestionsLocalData
import com.leptron.leptronquiz.data.remote.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuestionsLocalDataSource  internal constructor(
    private val questionsHistoryDao: QuestionHistoryDao ,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : QuestionsLocalData {

    override fun observeQuestions(): LiveData<List<QuestionHistory>>
    {
        return questionsHistoryDao.observeQuestions()
    }
    override suspend fun refreshQuestions()
    {
        //no-op
    }
    override suspend fun getQuestions(): ResultWrapper<List<QuestionHistory>> = withContext(ioDispatcher){
        return@withContext try {
            ResultWrapper.Success(questionsHistoryDao.getAllHistory())
        }
        catch (e: Exception){
            ResultWrapper.GenericError(DataError.DISK.ordinal, e.message)
        }
    }

    override suspend fun saveQuestion(questionHistory: QuestionHistory) = withContext(ioDispatcher) {
        questionsHistoryDao.insertHistory(questionHistory)
    }
    override suspend fun updateQuestionAnswer(questionHistory: QuestionHistory) = withContext(ioDispatcher) {
        questionsHistoryDao.updateAnswer(questionHistory.id, questionHistory.questionUserAnswer)
    }
    override suspend fun deleteAllTasks() = withContext(ioDispatcher) {
        questionsHistoryDao.deleteQuestions()
    }
}