package com.example.taskk

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDataBase : RoomDatabase() {
    //We need to specify all the interfaces,annotated by @Dao, so our database knows about the data access
    //methods defined for the Tables. Later this will be used by the classes that want to make instance of
    //the database class.
    abstract val taskDao: TaskDAO

    companion object {
        @Volatile
        private var INSTANCE: TaskDataBase? = null

        fun getInstance(context: Context): TaskDataBase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TaskDataBase::class.java,
                        "tasks_database"
                    ).build()
                    INSTANCE = instance

                }
                return instance

            }

        }
    }


}