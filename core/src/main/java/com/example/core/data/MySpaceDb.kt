package com.example.core.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.core.data.dao.CategoryDao
import com.example.core.data.entity.CategoryEntity

@Database(entities = [CategoryEntity::class], version = 1, exportSchema = false)
abstract class MySpaceDb: RoomDatabase() {
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var INSTANCE: MySpaceDb? = null

        fun getInstance(context: Context): MySpaceDb {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): MySpaceDb {
            return Room.databaseBuilder(context, MySpaceDb::class.java, "my_space")
                .fallbackToDestructiveMigration().build()
        }
    }
}