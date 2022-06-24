package com.example.taskk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ViewModelEditTaskFrag(private val dao: TaskDAO, private val taskID: Long) : ViewModel() {
    val task: LiveData<Task> = dao.get(taskID)
    private val _navigateBackToTaskF = MutableLiveData<Boolean>()
    val navigateBackToTaskF: LiveData<Boolean> get() = _navigateBackToTaskF
    //val taskNameVM: String =

    fun updateTask() {
        //???? what is viewModel scope, know more about them
        viewModelScope.launch {
            dao.update(task.value!!)
            _navigateBackToTaskF.value = true
        }
    }

    fun delete() {
        viewModelScope.launch {
            dao.delete(task.value!!)
            _navigateBackToTaskF.value = true
        }
    }

    fun onNavigationBackToTaskFCompleted() {
       _navigateBackToTaskF.value = false
    }


}