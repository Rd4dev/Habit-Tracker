package com.rd.habit_tracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NewTaskSheet : BottomSheetDialogFragment() {

    private lateinit var taskViewModel: TaskViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        taskViewModel = ViewModelProvider(activity).get(TaskViewModel::class.java)
        var saveButton = view?.findViewById<Button>(R.id.saveButton)
        saveButton?.setOnClickListener {
            saveAction()
        }
    }

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activity = requireActivity()
        taskViewModel = ViewModelProvider(activity).get(TaskViewModel::class.java)
        var saveButton = view?.findViewById<Button>(R.id.saveButton)
        saveButton?.setOnClickListener {
            saveAction()
        }
    }*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_task_sheet, container, false)
    }

    private fun saveAction(){
        taskViewModel.name.value = view?.findViewById<TextView>(R.id.name)?.text.toString()
        taskViewModel.desc.value = view?.findViewById<TextView>(R.id.description)?.text.toString()
        view?.findViewById<TextView>(R.id.name)?.text = ""
        view?.findViewById<TextView>(R.id.description)?.text = ""
        dismiss()
    }

}