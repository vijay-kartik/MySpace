package com.example.core.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.core.data.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Query("SELECT * from expenses")
    suspend fun getAllExpenses(): Flow<ExpenseEntity>

}