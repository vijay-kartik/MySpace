package com.vkartik.myspace.ui.presentation.home

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vkartik.myspace.ui.utils.extractBorderColorFrom
import com.vkartik.myspace.ui.utils.resizeBitmap

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), onSignOut: () -> Unit) {
    val selectedFile: String? by viewModel.fileSelected.collectAsStateWithLifecycle()
    val internetConnected: Boolean? by viewModel.internetConnected.collectAsStateWithLifecycle()
    val selectedBitmap: Uri? by viewModel.selectedImage.collectAsStateWithLifecycle()
    Text("Welcome signed in user")
    Spacer(modifier = Modifier.padding(10.dp))
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
            viewModel.onImageSelected(uri)
        }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.TopStart
    ) {
        Column {
            Button(onClick = {
                internetConnected?.let {
                    Toast.makeText(
                        context,
                        if (it) "Internet connected" else "Device Offline",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }) {
                Text(text = "Check internet status")
            }
            Button(onClick = {
                launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }) {
                Text(text = "Choose file to Upload")
            }
            if (selectedFile != null) {
                Text(text = "Selected file uri to upload: $selectedFile")
            }

            if (selectedBitmap != null) {
                val bitmap = ImageDecoder.decodeBitmap(
                    ImageDecoder.createSource(
                        context.contentResolver, selectedBitmap!!
                    )
                )
                val resizedBitmap = resizeBitmap(bitmap, 60)
                val toImageShape = RoundedCornerShape(16.dp)
                val borderStroke = BorderStroke(
                    2.dp, color = extractBorderColorFrom(
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
            Button(onClick = {
                viewModel.signOut {
                    onSignOut()
                }
            }) {
                Text(text = "Sign Out")
            }
        }
    }
}
