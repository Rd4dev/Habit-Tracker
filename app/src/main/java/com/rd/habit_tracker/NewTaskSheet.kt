package com.rd.habit_tracker

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NewTaskSheet(var taskItem: TaskItem?) : BottomSheetDialogFragment() {

    private lateinit var taskViewModel: TaskViewModel

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

        }else{
            var taskTitle = view.findViewById<TextView>(R.id.taskTitle)
            taskTitle.text = "New Task"
        }

        taskViewModel = ViewModelProvider(activity).get(TaskViewModel::class.java)
        var saveButton = view?.findViewById<Button>(R.id.saveButton)
        saveButton?.setOnClickListener {
            saveAction()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_task_sheet, container, false)
    }

    private fun saveAction(){
        val name = view?.findViewById<TextView>(R.id.name)?.text.toString()
        val desc = view?.findViewById<TextView>(R.id.description)?.text.toString()
        if(taskItem == null){
            val newTask = TaskItem(name, desc, null, null)
            taskViewModel.addTaskItem(newTask)
        }else{
            taskViewModel.updateTaskItem(taskItem!!.id, name, desc, null)
        }
        view?.findViewById<TextView>(R.id.name)?.text = ""
        view?.findViewById<TextView>(R.id.description)?.text = ""
        dismiss()
    }

}