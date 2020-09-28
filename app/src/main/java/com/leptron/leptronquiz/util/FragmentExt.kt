package com.leptron.leptronquiz.util

import androidx.fragment.app.Fragment
import com.leptron.leptronquiz.LeptronQuizApplication
import com.leptron.leptronquiz.ViewModelFactory

fun Fragment.getViewModelFactory(): ViewModelFactory {
    val remoteRepo =  (requireContext().applicationContext as LeptronQuizApplication).questionsRemoteRepository
    val localRepo = (requireContext().applicationContext as LeptronQuizApplication).questionsLocalRepository

    return ViewModelFactory(remoteRepo, localRepo,this)
}
