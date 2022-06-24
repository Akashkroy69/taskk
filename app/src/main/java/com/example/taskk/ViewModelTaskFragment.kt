package com.example.taskk

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class ViewModelTaskFragment(private val dao: TaskDAO) : ViewModel() {
    var newTaskName = ""

    //a property that is being observed
    private val _taskIdOnNavigation = MutableLiveData<Long?>()
    val taskIdOnNavigation: MutableLiveData<Long?> get() = _taskIdOnNavigation

    val tasks = dao.getAll()

    fun onTaskClicked(taskId: Long) {
        _taskIdOnNavigation.value = taskId
    }

    fun onTaskNavigated() {
        _taskIdOnNavigation.value = null
    }

    fun addTask() {
        viewModelScope.launch {
            val task = Task()
            task.taskName = newTaskName
            if (task.taskName != "")
                dao.insert(task)
        }
    }

    val tasksString = Transformations.map(tasks) { task ->
        formatTasks(task)
    }

    private fun formatTasks(tasks: List<Task>): String {

        return tasks.fold("") { str, item ->
            str + '\n' + formatTask(item)
        }
    }

    private fun formatTask(task: Task): String {
        var str = "ID: ${task.taskId}"
        str += "\nName: ${task.taskName}"
        str += "\nDone?: ${if (task.taskStatus) "YES" else "NO"}"
        return str
    }
}