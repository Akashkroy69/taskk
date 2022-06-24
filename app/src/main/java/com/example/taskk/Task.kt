package com.example.taskk

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var taskId: Long = 0L,

    @ColumnInfo(name = "task name")
    var taskName: String = "xyz",

    @ColumnInfo(name = "task status")
    var taskStatus: Boolean = false
)