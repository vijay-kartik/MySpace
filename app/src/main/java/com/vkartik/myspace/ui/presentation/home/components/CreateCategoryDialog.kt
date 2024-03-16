package com.vkartik.myspace.ui.presentation.home.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.core.ui.extensions.showToast

@Composable
fun CreateCategoryCard(onDismiss: () -> Unit) {
    var categoryName by rememberSaveable { mutableStateOf("") }
    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.widthIn(max = LocalConfiguration.current.screenWidthDp.dp - 80.dp),
        title = { Text(text = "Create Category") },
        text = {
            Column {
                Divider()
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    placeholder = { Text(text = "Enter category name") },
                    label = { Text(text = "Name") },
                    value = categoryName,
                    onValueChange = { categoryName = it })
            }

        },
        onDismissRequest = {
            Log.i("kartikd", "dismiss")
            onDismiss()
        }, confirmButton = {
            Text(
                text = "OK",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable(
                        enabled = if (categoryName.isNotEmpty()) true else false,
                        onClick = { onDismiss() }
                    )
            )
        }, dismissButton = {
            Text(
                text = "Dismiss",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable(
                        onClick = { onDismiss() }
                    )
            )
        })
}