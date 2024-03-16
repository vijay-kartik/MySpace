package com.vkartik.myspace.ui.presentation.home.components

import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.vkartik.myspace.domain.Category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCard(category: Category) {
    Card(onClick = { /*TODO*/ }) {
        Text(text = category.name)
    }
}