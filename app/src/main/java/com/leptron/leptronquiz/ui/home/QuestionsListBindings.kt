package com.leptron.leptronquiz.ui.home

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.leptron.leptronquiz.data.local.QuestionHistory

@BindingAdapter("items")
fun setItems(listView: RecyclerView, items: List<QuestionHistory>?) {
    items?.let {
        (listView.adapter as QuestionsAdapter).submitList(items)
    }
}

