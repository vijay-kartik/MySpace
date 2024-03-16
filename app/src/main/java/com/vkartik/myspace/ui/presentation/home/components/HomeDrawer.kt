package com.vkartik.myspace.ui.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DrawerContent() {
    ModalDrawerSheet(
        modifier = Modifier.padding(top = 64.dp),
        drawerContainerColor = Color.Transparent
    ) {
        Column(modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(start = 20.dp)) {
            Text(text = "Movies")
            Text(text = "Food log")
        }
    }
}
