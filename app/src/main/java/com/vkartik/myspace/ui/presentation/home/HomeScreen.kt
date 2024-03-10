package com.vkartik.myspace.ui.presentation.home

import android.content.Context
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
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
import coil.compose.AsyncImage
import com.vkartik.myspace.ui.utils.extractBorderColorFrom
import com.vkartik.myspace.ui.utils.resizeBitmap

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), onSignOut: () -> Unit) {
    val homeUiState: HomeUiState? by viewModel.homeUiState.collectAsStateWithLifecycle()
    val internetConnected: Boolean? by viewModel.internetConnected.collectAsStateWithLifecycle()
    val selectedBitmap: Uri? by viewModel.selectedImage.collectAsStateWithLifecycle()
    viewModel.showSignedInUserData()
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
            viewModel.onImageSelected(uri)
        }

    Column(modifier = Modifier.fillMaxWidth()) {
        UserDetail(homeUiState = homeUiState)
        Spacer(modifier = Modifier.padding(10.dp))
        HomeContent(
            context = context,
            viewModel = viewModel,
            onSignOut = onSignOut,
            selectedBitmap = selectedBitmap,
            launchImagePicker = {
                launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            },
            checkInternetStatus = {
                internetConnected?.let { connected ->
                    Toast.makeText(
                        context,
                        if (connected) "Internet connected" else "Device Offline",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
    }
}

@Composable
fun UserDetail(homeUiState: HomeUiState?) {
    homeUiState?.let {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text("Welcome ${it.userData.userName?.split(" ")?.first()}", modifier = Modifier.align(Alignment.CenterVertically))

            AsyncImage(
                model = it.userData.profilePicUrl,
                contentDescription = null,
                modifier = Modifier.clip(CircleShape),
                alignment = Alignment.TopEnd
            )
        }
    }
}

@Composable
fun HomeContent(
    context: Context,
    viewModel: HomeViewModel,
    onSignOut: () -> Unit,
    selectedBitmap: Uri?,
    launchImagePicker: () -> Unit,
    checkInternetStatus: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(16.dp), contentAlignment = Alignment.TopStart
    ) {
        Column {
            Button(onClick = {
                checkInternetStatus()
            }) {
                Text(text = "Check internet status")
            }
            Button(onClick = {
                launchImagePicker()
            }) {
                Text(text = "Choose file to Upload")
            }

            ImageUploads(context = context, selectedBitmap = selectedBitmap)
            SignOutButton {
                viewModel.signOut {
                    onSignOut()
                }
            }
        }
    }
}

@Composable
fun ImageUploads(context: Context, selectedBitmap: Uri?) {
    if (selectedBitmap != null) {
        val bitmap = ImageDecoder.decodeBitmap(
            ImageDecoder.createSource(
                context.contentResolver, selectedBitmap
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
}

@Composable
fun SignOutButton(onSignOut: () -> Unit) {
    Button(onClick = {
        onSignOut()
    }) {
        Text(text = "Sign Out")
    }
}
