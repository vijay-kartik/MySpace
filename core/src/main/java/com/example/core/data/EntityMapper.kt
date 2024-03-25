package com.example.core.data

import com.example.core.data.entity.CategoryEntity
import com.example.core.domain.Category

fun Category.toEntity(): CategoryEntity = CategoryEntity(
    uid = uid!!,
    name = name,
    storagePath = storagePath
)

fun CategoryEntity.toDomain(): Category = Category(
    uid = uid,
    name = name,
    storagePath = storagePath
)