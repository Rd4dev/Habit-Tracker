package com.rd.habit_tracker

import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalTime

class NewTaskSheet(var taskItem: TaskItem?) : BottomSheetDialogFragment() {

    private lateinit var taskViewModel: TaskViewModel
    private var dueTime: LocalTime? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        if(taskItem != null){
            var taskTitle = view.findViewById<TextView>(R.id.taskTitle)
            taskTitle.text = "Edit Task"
            val editable = Editable.Factory.getInstance()
            val name = view.findViewById<TextView>(R.id.name)
            name.text = editable.newEditable(taskItem!!.name)
            val desc = view.findViewById<TextView>(R.id.description)
            desc.text = editable.newEditable(taskItem!!.desc)

            if(taskItem!!.dueTime() != null){
                dueTime = taskItem!!.dueTime()
                updateTimeButtonText()
            }

        }else{
            var taskTitle = view.findViewById<TextView>(R.id.taskTitle)
            taskTitle.text = "New Task"
        }

        taskViewModel = ViewModelProvider(activity).get(TaskViewModel::class.java)
        var saveButton = view?.findViewById<Button>(R.id.saveButton)
        saveButton?.setOnClickListener {
            saveAction()
        }

        val timePickerButton = view.findViewById<Button>(R.id.timePickerButton)
        timePickerButton.setOnClickListener {
            openTimePicker()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun openTimePicker() {
        if(dueTime == null)
            dueTime = LocalTime.now()
        val listener = TimePickerDialog.OnTimeSetListener{_, selectedHour, selectedMinute ->
            dueTime = LocalTime.of(selectedHour, selectedMinute)
            updateTimeButtonText()
        }
        val dialog = TimePickerDialog(activity, listener, dueTime!!.hour, dueTime!!.minute, true)
        dialog.setTitle("Task Due")
        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateTimeButtonText() {
        val timePickerButton = view?.findViewById<TextView>(R.id.timePickerButton)
        timePickerButton?.text = String.format("%02d:%02d",dueTime!!.hour,dueTime!!.minute)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_task_sheet, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveAction(){
        val name = view?.findViewById<TextView>(R.id.name)?.text.toString()
        val desc = view?.findViewById<TextView>(R.id.description)?.text.toString()
        val dueTimeString = if(dueTime == null) null else TaskItem.timeFormatter.format(dueTime)
        if(taskItem == null){
            val newTask = TaskItem(name, desc, dueTimeString, null)
            taskViewModel.addTaskItem(newTask)
        }else{
            taskItem!!.name = name
            taskItem!!.desc = desc
            taskItem!!.dueTimeString = dueTimeString

            taskViewModel.updateTaskItem(taskItem!!)
        }
        view?.findViewById<TextView>(R.id.name)?.text = ""
        view?.findViewById<TextView>(R.id.description)?.text = ""
        dismiss()
    }

}