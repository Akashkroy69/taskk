package com.example.taskk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ViewModelAddTaskF(private val dao: TaskDAO) : ViewModel() {
    var newTaskNameString: String = ""
    var newTaskStatus = false
   private val _onSaveNavigateToTaskF = MutableLiveData<Boolean>()
    val onSaveNavigateToTaskF: LiveData<Boolean> get() = _onSaveNavigateToTaskF

    fun addTask() {
        viewModelScope.launch {
            val task = Task()
            task.taskName = newTaskNameString
            task.taskStatus = newTaskStatus
            if (newTaskNameString != "") {
                dao.insert(task)
                _onSaveNavigateToTaskF.value = true
            }
        }
    }

    fun onNavigationToTaskF(){
        _onSaveNavigateToTaskF.value = false
    }
}