package com.example.taskk

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class FactoryAddTaskFVM(private val dao: TaskDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelAddTaskF::class.java))
            return ViewModelAddTaskF(dao) as T
        throw IllegalArgumentException("Unknown ViewModel")

    }
}