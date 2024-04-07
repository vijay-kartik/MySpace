package com.vkartik.myspace.ui.presentation.home.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.ui.components.RoundedCornerImage
import com.example.core.ui.extensions.getBitmapFromUri
import com.vkartik.myspace.ui.presentation.home.HomeViewModel

@Composable
fun CreateCategoryDialog(viewModel: HomeViewModel, onDismiss: () -> Unit) {
    var categoryName by rememberSaveable { mutableStateOf("") }
    var selectedImageUri by rememberSaveable {
        mutableStateOf<Uri?>(null)
    }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
            selectedImageUri = uri
        }
    val showProgress by viewModel.startProgress.collectAsStateWithLifecycle()
    
    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.widthIn(max = LocalConfiguration.current.screenWidthDp.dp - 80.dp),
        title = { Text(text = "Create Category") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Divider()
                TextField(
                    placeholder = { Text(text = "Enter category name") },
                    label = { Text(text = "Name") },
                    value = categoryName,
                    onValueChange = { categoryName = it }
                )
                if (showProgress) {
                    LinearProgressIndicator(modifier = Modifier.widthIn(max = LocalConfiguration.current.screenWidthDp.dp - 90.dp))
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(onClick = {
                        launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }) {
                        Text(text = "Choose an image")
                    }
                    selectedImageUri?.let {
                        val bitmap = LocalContext.current.getBitmapFromUri(it)
                        RoundedCornerImage(
                            original = bitmap,
                            resizeWidth = 40,
                            resizeHeight = 40,
                            cornerRadius = 4.dp,
                            borderWidth = 1.dp
                        )
                    }
                }
            }

        },
        onDismissRequest = {
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
                        onClick = {
                            viewModel.createCategory(selectedImageUri, categoryName)
                        }
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