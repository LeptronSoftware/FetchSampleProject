package com.leptron.leptronquiz.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.leptron.leptronquiz.data.local.QuestionHistory
import com.leptron.leptronquiz.databinding.FragQuestionItemBinding


/**
 * Adapter for the task list. Has a reference to the [HomeViewModel] to send actions back to it.
 */
class QuestionsAdapter(private val viewModel: HomeViewModel) :
    ListAdapter<QuestionHistory, QuestionsAdapter.ViewHolder>(QuestionDiffCallback()) {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return viewModel.items.value?.get(position)?.id?.toLong() ?: 0L
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(viewModel, item)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        // Using payload to refresh partly
        if (payloads.isNullOrEmpty()) {
            // refresh all
            onBindViewHolder(holder, position)
            return
        }
        //do partial update if we have changes
        holder.apply{
            val item = getItem(position)
            holder.bindPartial( item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: FragQuestionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: HomeViewModel, item: QuestionHistory) {
            binding.viewmodel=viewModel
            binding.questionHistory = item
            binding.questionHistoryDelta=item
            binding.executePendingBindings()

        }
        fun bindPartial(item: QuestionHistory) {
            binding.questionHistoryDelta = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragQuestionItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class QuestionDiffCallback : DiffUtil.ItemCallback<QuestionHistory>() {
    override fun areItemsTheSame(oldItem: QuestionHistory, newItem: QuestionHistory): Boolean {
        return oldItem.id == newItem.id

    }

    override fun areContentsTheSame(oldItem: QuestionHistory, newItem: QuestionHistory): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: QuestionHistory, newItem: QuestionHistory): Any? {
        return newItem
    }

}