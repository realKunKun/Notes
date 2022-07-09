package com.example.notes.logic.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
@androidx.room.Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class Database: RoomDatabase() {
    //获取数据表操作实例
    abstract fun dataDao(): DataDao

    //单例模式
    companion object {
        private const val DB_NAME = "app_database"
        @Volatile
        private var INSTANCE: Database? = null
        fun getDatabase(context: Context): Database{
            val tempInstance = INSTANCE
            if(tempInstance != null) { return tempInstance }
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                    Database::class.java, DB_NAME).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}