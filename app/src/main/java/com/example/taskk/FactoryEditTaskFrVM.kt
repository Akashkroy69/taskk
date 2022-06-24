package com.example.taskk

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class FactoryEditTaskFrVM(private val dao: TaskDAO, private val taskID: Long) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelEditTaskFrag::class.java))
            return ViewModelEditTaskFrag(dao, taskID) as T
        throw IllegalArgumentException("Unknown ViewModel")

    }

}