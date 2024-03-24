package com.vkartik.myspace.ui.presentation.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core.domain.Category
import com.example.core.ui.components.RoundedCornerImage
import com.example.core.ui.extensions.getBitmapFromLocalPath
import com.vkartik.myspace.ui.presentation.home.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CategoryCard(category: Category, coroutineScope: CoroutineScope, viewModel: HomeViewModel) {
    var imagePath: String? by remember { mutableStateOf(null) }

    Card(onClick = { /*TODO*/ }) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = category.name, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(5.dp))
            category.storagePath?.let {
                coroutineScope.launch {
                    imagePath = viewModel.getCategoryImage(it)
                }
                if (imagePath != null) {
                    CategoryImage(localFilePath = imagePath!!)
                    Spacer(modifier = Modifier.height(5.dp))
                }

            }
        }
    }
}

@Composable
fun CategoryImage(localFilePath: String) {
    val bitmap = LocalContext.current.getBitmapFromLocalPath(localFilePath)
    RoundedCornerImage(
        original = bitmap,
        resizeWidth = 150,
        resizeHeight = 150,
        cornerRadius = 8.dp,
        borderWidth = 1.dp
    )
}