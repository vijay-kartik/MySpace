package com.example.core.data

import com.example.core.data.entity.CategoryEntity
import com.example.core.domain.Category


fun Category.toEntity(): CategoryEntity {
    return CategoryEntity(
        name = name,
        imageUri = imageUri.toString(),
        storagePath = storagePath
    )
}