package com.leptron.leptronquiz.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question_history")
data class QuestionHistory @JvmOverloads constructor(
    @ColumnInfo(name="question_text") var questionText: String="",
    @ColumnInfo(name="question_correct_answer") var questionCorrectAnswer : String= "",
    @ColumnInfo(name="question_answer") var questionUserAnswer : String= "",

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name="entryid") var id : Int = 0
) {

}