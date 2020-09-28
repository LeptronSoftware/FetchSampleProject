package com.leptron.leptronquiz.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import com.leptron.leptronquiz.databinding.FragQuestionsBinding
import com.leptron.leptronquiz.util.getViewModelFactory
import timber.log.Timber

class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeViewModel> { getViewModelFactory() }
    private lateinit var viewDataBinding: FragQuestionsBinding
    private lateinit var listAdapter: QuestionsAdapter
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewDataBinding=FragQuestionsBinding.inflate(inflater,container,false).apply {
            viewmodel=viewModel
        }

        return viewDataBinding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupListAdapter()
    }
    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            listAdapter = QuestionsAdapter(viewModel)
            listAdapter.setHasStableIds(true)
            viewDataBinding.questionsList.adapter = listAdapter
            viewDataBinding.questionsList.itemAnimator = DefaultItemAnimator()

        } else {
            Timber.w("ViewModel not initialized when attempting to set up adapter.")
        }
    }
}