package com.rd.habit_tracker

import android.content.Context
import androidx.core.content.ContextCompat
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class TaskItem(
    var name: String,
    var desc: String,
    var dueTime: LocalTime?,
    var completedDate: LocalDate?,
    var id: UUID = UUID.randomUUID()
) {
    fun isCompleted() = completedDate != null
    fun imageResource(): Int = if(isCompleted()) R.drawable.ic_checked_24 else R.drawable.ic_check_box_24
    fun imageColor(context: Context) = if(isCompleted()) black(context) else black(context)
    fun cardBackgroundColor(context: Context) = if(isCompleted())  green(context) else white(context)

    private fun green(context: Context) = ContextCompat.getColor(context, R.color.green)
    private fun white(context: Context) = ContextCompat.getColor(context, R.color.white)
    private fun black(context: Context) = ContextCompat.getColor(context, R.color.black)
}