package com.vkartik.myspace.ui.presentation.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vkartik.myspace.ui.presentation.sign_in.UserData

@Composable
fun ProfileIconButton(userData: UserData?, signOut: () -> Unit) {
    var isContextMenuVisible by rememberSaveable { mutableStateOf(false) }
    var pressOffSet by remember { mutableStateOf(DpOffset(0.dp, 0.dp)) }
    var itemHeight by remember { mutableStateOf(0.dp) }
    var itemWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    IconButton(onClick = { isContextMenuVisible = true }) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .onSizeChanged {
                    itemHeight = with(density) { it.height.toDp() }
                    itemWidth = with(density) { it.width.toDp() }
                }
        ) {
            AsyncImage(
                model = userData?.profilePicUrl,
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
            )

            DropdownMenu(
                expanded = isContextMenuVisible,
                onDismissRequest = { isContextMenuVisible = false },
                offset = pressOffSet.copy(y = pressOffSet.y + 8.dp, x = itemWidth - 30.dp)
            ) {
                DropdownMenuItem(onClick = {
                    isContextMenuVisible = false
                    signOut()
                }, text = { Text(text = "Sign Out") })
            }
        }
    }
}