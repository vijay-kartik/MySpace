package com.vkartik.myspace.ui.presentation.home.components

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core.ui.components.RoundedCornerImage
import com.vkartik.myspace.domain.Category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCard(category: Category) {
    Card(onClick = { /*TODO*/ }) {
        Column(modifier = Modifier
            .padding(16.dp)
            .align(Alignment.CenterHorizontally)) {
            Text(text = category.name, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(5.dp))
            CategoryImage(selectedBitmap = category.imageUri)
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun CategoryImage(selectedBitmap: Uri?) {
    if (selectedBitmap != null) {
        RoundedCornerImage(
            uri = selectedBitmap,
            resizeWidth = 150,
            resizeHeight = 150,
            cornerRadius = 8.dp,
            borderWidth = 1.dp
        )
    }
}