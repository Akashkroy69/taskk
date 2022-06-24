package com.example.taskk

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallBackForTaskItem : DiffUtil.ItemCallback<Task>() {

    //if this returns true then only areContentsTheSame() is called.
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = (oldItem.taskId == newItem.taskId)

    override fun areContentsTheSame(oldItem: Task, newItem: Task) = (oldItem == newItem)
}