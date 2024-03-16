package com.vkartik.myspace.ui.presentation.home.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.vkartik.myspace.ui.presentation.home.HomeViewModel
import com.vkartik.myspace.ui.utils.extractBorderColorFrom
import com.vkartik.myspace.ui.utils.resizeBitmap

@Composable
fun CreateCategoryCard(viewModel: HomeViewModel, onDismiss: () -> Unit) {
    var categoryName by rememberSaveable { mutableStateOf("") }
    var selectedImageUri by rememberSaveable {
        mutableStateOf<Uri?>(null)
    }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
            selectedImageUri = uri
        }
    
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
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(onClick = {
                        launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }) {
                        Text(text = "Choose an image")
                    }
                    if (selectedImageUri != null) {
                        val bitmap = ImageDecoder.decodeBitmap(
                            ImageDecoder.createSource(
                                LocalContext.current.contentResolver, selectedImageUri!!
                            )
                        )
                        val resizedBitmap = resizeBitmap(bitmap, 40.dp, LocalDensity.current)
                        val toImageShape = RoundedCornerShape(4.dp)
                        val borderStroke = BorderStroke(
                            1.dp, color = extractBorderColorFrom(
                                resizedBitmap.copy(
                                    Bitmap.Config.ARGB_8888, true
                                )
                            )
                        )
                        Image(
                            resizedBitmap.asImageBitmap(),
                            null,
                            Modifier
                                .clip(toImageShape)
                                .border(borderStroke, toImageShape)
                        )
                    }

                }

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
                        onClick = {
                            viewModel.onCategoryCreated(selectedImageUri, categoryName)
                            onDismiss()
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