package com.example.core.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val createdAt: Timestamp,
    val what: String,
    val amount: Double,
    val currency: Int = 0,
    val account: String,
    val accountType: String,
    val expenseType: String,
    val notes: String = ""
)