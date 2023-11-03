package com.rd.habit_tracker

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

@Entity(tableName = "task_item_table")
class TaskItem(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "desc") var desc: String,
    @ColumnInfo(name = "startDateString") var startDateString: String?,
    @ColumnInfo(name = "endDateString") var endDateString: String?,
    @ColumnInfo(name = "completedDateString") var completedDateString: String?,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun completedDate(): LocalDate? = if(completedDateString == null) null
    else LocalDate.parse(completedDateString, dateFormatter)

//    @RequiresApi(Build.VERSION_CODES.O)
//    fun dueTime(): LocalTime? = if(dueTimeString == null) null
//    else LocalTime.parse(dueTimeString, timeFormatter)

    @RequiresApi(Build.VERSION_CODES.O)
    fun startDate(): LocalDate? = if (startDateString == null) null
    else LocalDate.parse(startDateString, dateFormatter)

    @RequiresApi(Build.VERSION_CODES.O)
    fun endDate(): LocalDate? = if (endDateString == null) null
    else LocalDate.parse(endDateString, dateFormatter)

    @RequiresApi(Build.VERSION_CODES.O)
    fun isCompleted() = completedDate() != null
    @RequiresApi(Build.VERSION_CODES.O)
    fun imageResource(): Int = if(isCompleted()) R.drawable.ic_checked_24 else R.drawable.ic_check_box_24
    @RequiresApi(Build.VERSION_CODES.O)
    fun imageColor(context: Context) = if(isCompleted()) black(context) else black(context)
    @RequiresApi(Build.VERSION_CODES.O)
    fun cardBackgroundColor(context: Context) = if(isCompleted())  green(context) else white(context)

    private fun green(context: Context) = ContextCompat.getColor(context, R.color.green)
    private fun white(context: Context) = ContextCompat.getColor(context, R.color.white)
    private fun black(context: Context) = ContextCompat.getColor(context, R.color.black)

    companion object{
        @RequiresApi(Build.VERSION_CODES.O)
        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_TIME
        @RequiresApi(Build.VERSION_CODES.O)
        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE

    }

}