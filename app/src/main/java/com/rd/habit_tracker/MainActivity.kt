package com.rd.habit_tracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        var newTaskButton = findViewById<Button>(R.id.newTaskButton)
        newTaskButton.setOnClickListener {
            NewTaskSheet().show(supportFragmentManager, "newTaskTag")
        }

        taskViewModel.name.observe(this){
            var taskName = findViewById<TextView>(R.id.taskName)
            taskName.text = String.format("Task Name: %s", it)
        }
        taskViewModel.desc.observe(this){
            var taskDesc = findViewById<TextView>(R.id.taskDesc)
            taskDesc.text = String.format("Task Desc: %s", it)
        }

    }
}