package com.leptron.leptronquiz.ui.home

import androidx.lifecycle.*
import com.leptron.leptronquiz.data.QuestionsLocalData
import com.leptron.leptronquiz.data.local.QuestionHistory
import com.leptron.leptronquiz.data.remote.QuestionsRemoteData
import com.leptron.leptronquiz.data.remote.ResultWrapper
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel(
    private val questionRemoteRepository: QuestionsRemoteData,
    private val questionLocalRepository: QuestionsLocalData
) : ViewModel() {

    private val _forceUpdate = MutableLiveData<Boolean>(false)
    //only observe new values and process if the Room operation is a success or failure
    private val _items: LiveData<List<QuestionHistory>> =
        _forceUpdate.switchMap { forceUpdate ->
            if (forceUpdate) {
                _dataLoading.value = true
                viewModelScope.launch {
                    when (val localResult = questionLocalRepository.getQuestions()) {
                        is ResultWrapper.Success -> {
                            //items will be set by _items which will be set by the retrofit call that will insert into the db and cuase room
                            //to trigger the livedata updates
                            if(localResult.value.isEmpty())
                                questionRemoteRepository.refreshQuestions()
                        }
                    }
                    _dataLoading.value = false
                }
            }
            //switch to the new live data list after each lambda function is executed, thus the name switchMap
            questionLocalRepository.observeQuestions()
        }




    val items: LiveData<List<QuestionHistory>> = _items

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _noTaskIconRes = MutableLiveData<Int>()
    val noTaskIconRes: LiveData<Int> = _noTaskIconRes

    private val _noTasksLabel = MutableLiveData<Int>()
    val noTasksLabel: LiveData<Int> = _noTasksLabel

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    init {
        // Set initial state
        loadTasks(true)
    }

    fun loadTasks(forceUpdate: Boolean) {
        //trigger _forceUpdate livedats onchanged event to refresh items datastructures
        _forceUpdate.value = forceUpdate
    }
    fun refresh() {
        _forceUpdate.value = true
    }
    // This LiveData depends on another so we can use a transformation.
    val empty: LiveData<Boolean> = Transformations.map(_items) {
        it.isEmpty()
    }
    private fun loadResultToLiveData(questionsResult:  List<QuestionHistory>): LiveData<List<QuestionHistory>> {

        val returnValue = MutableLiveData<List<QuestionHistory>>()

        //if (questionsResult is ResultWrapper.Success) {
            returnValue.value =  questionsResult

        //} else {
           // returnValue.value = emptyList()
            //showSnackbarMessage(R.string.loading_tasks_error)
            //TODO: show error mesage
        //}

        return returnValue
    }
    fun getResultText(qh: QuestionHistory) : String
    {
        if(qh.questionUserAnswer.isBlank())
            return ""
        if(qh.questionUserAnswer.toUpperCase(Locale.getDefault()).equals(qh.questionCorrectAnswer.toUpperCase(Locale.getDefault())))
            return "Correct!"
        else
            return "Wrong!"
    }

    fun getResultColor(qh: QuestionHistory) : Int
    {
        if(qh.questionUserAnswer.toUpperCase(Locale.getDefault()).equals(qh.questionCorrectAnswer.toUpperCase(Locale.getDefault())))
            return com.leptron.leptronquiz.R.color.colorGreenLight
        else
            return com.leptron.leptronquiz.R.color.colorRedLight
    }
    fun  getBtnBackgroundTrue(qh: QuestionHistory) :  Int
    {
        if(qh.questionUserAnswer=="true")
            return com.leptron.leptronquiz.R.drawable.quiz_button_true_selector_selected
        else
            return com.leptron.leptronquiz.R.drawable.quiz_button_true_selector
    }
    fun  getBtnBackgroundFalse(qh: QuestionHistory) :  Int
    {
        if( qh.questionUserAnswer=="false")
            return com.leptron.leptronquiz.R.drawable.quiz_button_false_selector_selected
        else
            return com.leptron.leptronquiz.R.drawable.quiz_button_false_selector
    }
    //set background in databinding
    fun setAnswer(qh: QuestionHistory, answer: Boolean) = viewModelScope.launch {
        val qhUpdate = qh.copy()
        if(answer) {
            qhUpdate.questionUserAnswer = "true"
        }
        else {
            qhUpdate.questionUserAnswer = "false"
        }
        questionLocalRepository.updateQuestionAnswer(qhUpdate)

    }

}