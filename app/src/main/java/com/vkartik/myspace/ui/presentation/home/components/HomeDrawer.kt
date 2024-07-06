package com.vkartik.myspace.ui.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.vkartik.myspace.ui.presentation.SubScreens

@Composable
fun DrawerContent(onDrawerItemClick: (SubScreens) -> Unit) {
    ModalDrawerSheet(
        modifier = Modifier.padding(top = 70.dp),
        drawerContainerColor = Color.Transparent
    ) {
        Column(modifier = Modifier.widthIn((LocalConfiguration.current.screenWidthDp / 2).dp).fillMaxHeight()
            .background(MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(topEnd = 20.dp))
            .padding(start = 20.dp)
        ) {
            val modifier = Modifier.padding(vertical = 10.dp)
            Text(text = "Movies", modifier.clickable { onDrawerItemClick(SubScreens.DEFAULT) })
            Text(text = "Expenses", modifier.clickable { onDrawerItemClick(SubScreens.EXPENSES) })
        }
    }
}
