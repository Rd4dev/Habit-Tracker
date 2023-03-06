package com.rd.habit_tracker

import android.content.Context
import android.graphics.Paint
import android.media.Image
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.time.format.DateTimeFormatter

class TaskItemViewHolder(
    private val context: Context,
    itemView: View,
    private val clickListener: TaskItemClickListener
): RecyclerView.ViewHolder(itemView) {
    @RequiresApi(Build.VERSION_CODES.O)
    val timeFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    @RequiresApi(Build.VERSION_CODES.O)
    fun bindTaskItem(taskItem: TaskItem){
        val name = itemView.findViewById<TextView>(R.id.name)
        name.text = taskItem.name

        val dueTime = itemView.findViewById<TextView>(R.id.dueTime)
        if(taskItem.isCompleted()){
            name.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            dueTime.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }

        val cardTaskCell = itemView.findViewById<CardView>(R.id.taskCellContainer)
        cardTaskCell.setCardBackgroundColor(taskItem.cardBackgroundColor(context))

        val completeButton = itemView.findViewById<ImageView>(R.id.completeButton)
        completeButton.setImageResource(taskItem.imageResource())
        completeButton.setColorFilter(taskItem.imageColor(context))

        completeButton.setOnClickListener {
            clickListener.completeTaskItem(taskItem)
        }
        completeButton.setOnClickListener {
            clickListener.completeTaskItem(taskItem)
        }

        if (taskItem.dueTime() != null){
            val dueTime = itemView.findViewById<TextView>(R.id.dueTime)
            dueTime.text = timeFormat.format(taskItem.dueTime())
        }else{
            val dueTime = itemView.findViewById<TextView>(R.id.dueTime)
            dueTime.text = ""
        }
    }
}