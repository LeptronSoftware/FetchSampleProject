package com.leptron.leptronquiz.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuestionHistoryDao {
    //get history
    @Query("SELECT * FROM question_history") fun getAllHistory(): List<QuestionHistory>

    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertHistory(questionHistory: QuestionHistory)
    /**
     * Observes list of tasks.
     *
     * @return all tasks.
     */
    @Query("SELECT * FROM question_history")
    fun observeQuestions(): LiveData<List<QuestionHistory>>

    /**
     * Update the complete status of a task
     *
     * @param taskId id of the task
     * @param completed status to be updated
     */
    @Query("UPDATE question_history SET question_answer = :answer WHERE entryid = :questionID")
    suspend fun updateAnswer(questionID: Int, answer: String)
    /**
     * Observes a single task.
     *
     * @param taskId the task id.
     * @return the task with taskId.
     */
    @Query("SELECT * FROM question_history WHERE entryid = :taskId")
    fun observeTaskById(taskId: Int): LiveData<QuestionHistory>

    @Query("DELETE FROM question_history")
    suspend fun deleteQuestions()
}