package com.rd.habit_tracker

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
/*import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry*/
import com.google.android.material.textfield.TextInputEditText
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.time.YearMonth
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

class MainActivity : AppCompatActivity(), TaskItemClickListener {

    private val colors2 = intArrayOf(
        R.color.green_700, R.color.green_500, R.color.green_200, R.color.grey_200
    )

    private val taskViewModel: TaskViewModel by viewModels {
        TaskItemModelFactory((application as TodoApplication).repository)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        var newTaskButton = findViewById<Button>(R.id.newTaskButton)
        newTaskButton.setOnClickListener {
            NewTaskSheet(null).show(supportFragmentManager, "newTaskTag")
        }

        setRecyclerView()

        //current month
        val date = Date()
        val month = SimpleDateFormat("MMMM").format(date)

        //set current month name
        val monthText = findViewById<TextView>(R.id.tv_month)
        monthText.text = month

        //get number of days in current year and current month
        val calender = Calendar.getInstance()

        //month indexed from 0
        val currentMonth = calender.get(Calendar.MONTH)
        var accurateCurrentMonth = currentMonth + 1

        loadMonth(accurateCurrentMonth)

        val nextMonthBtn = findViewById<ImageView>(R.id.nextMonth)
        nextMonthBtn.setOnClickListener {
            if (accurateCurrentMonth != 12) {
                loadMonth(accurateCurrentMonth + 1)
                accurateCurrentMonth += 1
            } else {
                accurateCurrentMonth = 1
                loadMonth(accurateCurrentMonth)
            }
        }

        val prevMonthBtn = findViewById<ImageView>(R.id.prevMonth)
        prevMonthBtn.setOnClickListener {
            if (accurateCurrentMonth != 1) {
                loadMonth(accurateCurrentMonth - 1)
                accurateCurrentMonth -= 1
            } else {
                accurateCurrentMonth = 12
                loadMonth(accurateCurrentMonth)
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadMonth(month: Int) {
        val heatMap = findViewById<GridLayout>(R.id.heatMap)
        heatMap.removeAllViews()

        val monthText = findViewById<TextView>(R.id.tv_month)
        monthText.text = getMonthName(month)

        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val yearMonth = YearMonth.of(currentYear, month)
        val daysInMonth = yearMonth.lengthOfMonth()


        for (i in 0 until 5) {
            for (j in 0 until 7) {
                val box = TextView(this)
                val r = Random().nextInt(4)

                box.setBackgroundResource(R.drawable.ic_baseline_texture_24)

                val shape = GradientDrawable()
                shape.shape = GradientDrawable.RECTANGLE
                shape.cornerRadius = 5.dpToPx()
                shape.setColor(ContextCompat.getColor(this, colors2[r]))
                // set desired color here
                if ((i * 7 + j + 1) <= daysInMonth) {
                    box.background = shape
                    box.text = "${i * 7 + j + 1}"
                    box.gravity = Gravity.CENTER
                }

                val params = GridLayout.LayoutParams(
                    GridLayout.spec(i, 1f),
                    GridLayout.spec(j, 1f)
                )
                params.width = 0
                params.height = 0
                params.setMargins(10, 10, 10, 10)

                heatMap.addView(box, params)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setRecyclerView(){
        val mainActivity = this
        taskViewModel.taskItems.observe(this){
            val todoListRecyclerView = findViewById<RecyclerView>(R.id.todoListRecyclerView)
//            val tasks = todoListRecyclerView.apply { it }
//            val sortedTasks = todoListRecyclerView.apply { it.sortedBy { !it.isCompleted() } }
            todoListRecyclerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = TaskItemAdapter(it.sortedBy { it.isCompleted() }, mainActivity)
            }
        }
    }

    override fun editTaskItem(taskItem: TaskItem) {
        NewTaskSheet(taskItem).show(supportFragmentManager, "newTaskTag")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun completeTaskItem(taskItem: TaskItem) {
        taskViewModel.setCompleted(taskItem)
    }
}

fun Int.dpToPx(): Float {
    val scale = Resources.getSystem().displayMetrics.density
    return (this * scale + 0.5f)
}
