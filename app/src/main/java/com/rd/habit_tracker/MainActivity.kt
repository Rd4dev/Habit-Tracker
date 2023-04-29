package com.rd.habit_tracker

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.TextView
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
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

class MainActivity : AppCompatActivity(), TaskItemClickListener {

    private val taskViewModel: TaskViewModel by viewModels {
        TaskItemModelFactory((application as TodoApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

//        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        var newTaskButton = findViewById<Button>(R.id.newTaskButton)
        newTaskButton.setOnClickListener {
            NewTaskSheet(null).show(supportFragmentManager, "newTaskTag")
        }

        setRecyclerView()

        /*val anyChartView = findViewById<AnyChartView>(R.id.chart_view)

        val heatMap = AnyChart.heatMap()
        val data = ArrayList<DataEntry>()

        for(i in 0 until 30){
            for(j in 0 until 7){
                val value = Random().nextInt(5)
                data.add(ValueDataEntry("$i"+"$j",value))
            }
        }

        heatMap.data(data)

        anyChartView.setChart(heatMap)*/

        val colors = arrayOf(
            Color.parseColor("#30A14E"),
            Color.parseColor("#40C463"),
            Color.parseColor("#9BE9A8"),
            Color.parseColor("#EAECEF")
        )

        val colors2 = intArrayOf(
            R.color.green_700, R.color.green_500, R.color.green_200, R.color.grey_200
        )

        /*val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.cornerRadius = 5.dpToPx()*/
//        shape.setColor(ContextCompat.getColor(this, colors2[2])) // set desired color here


        for (i in 0 until 4) {
            for (j in 0 until 7) {
                val box = TextView(this)
                val r = Random().nextInt(4)
                box.setBackgroundResource(R.drawable.grid_background)

                val shape = GradientDrawable()
                shape.shape = GradientDrawable.RECTANGLE
                shape.cornerRadius = 5.dpToPx()
                shape.setColor(ContextCompat.getColor(this, colors2[r])) // set desired color here

                box.background = shape

//                box.setBackgroundColor(colors[r])
                val params = GridLayout.LayoutParams(
                    GridLayout.spec(i, 1f),
                    GridLayout.spec(j, 1f)
                )
                params.width = 0
                params.height = 0
                params.setMargins(10, 10, 10, 10)
                val heatMap = findViewById<GridLayout>(R.id.heatMap)
                heatMap.addView(box, params)

                box.text = "${i * 7 + j + 1}"
                box.gravity = Gravity.CENTER
            }
        }

    }

    private fun setRecyclerView(){
        val mainActivity = this
        taskViewModel.taskItems.observe(this){
            val todoListRecyclerView = findViewById<RecyclerView>(R.id.todoListRecyclerView)
            todoListRecyclerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = TaskItemAdapter(it, mainActivity)
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
    return (this * scale + 0.5f).toFloat()
}
