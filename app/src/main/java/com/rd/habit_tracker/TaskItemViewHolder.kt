package com.rd.habit_tracker

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskItemViewHolder(
    private val context: Context, itemView: View,
): RecyclerView.ViewHolder(itemView) {
    fun bindTaskItem(taskItem: TaskItem){
        val name = itemView.findViewById<TextView>(R.id.name)
        name.text = taskItem.name
    }
}