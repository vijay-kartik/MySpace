package com.example.core.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.data.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories")
    fun getAll(): Flow<List<CategoryEntity>>

    @Insert(CategoryEntity::class, OnConflictStrategy.REPLACE)
    suspend fun insert(category: CategoryEntity)

    @Query("DELETE FROM categories")
    suspend fun deleteAll()

    @Query("DELETE from categories where name = :name")
    suspend fun deleteByName(name: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<CategoryEntity>)
}