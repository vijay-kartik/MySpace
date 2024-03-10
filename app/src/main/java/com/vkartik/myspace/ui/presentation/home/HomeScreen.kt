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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.vkartik.myspace.ui.presentation.sign_in.UserData
import com.vkartik.myspace.ui.utils.extractBorderColorFrom
import com.vkartik.myspace.ui.utils.resizeBitmap

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), navigateBackToSignIn: () -> Unit) {
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
        UserDetail(userData = homeUiState?.userData) { viewModel.signOut { navigateBackToSignIn() } }
        Spacer(modifier = Modifier.padding(10.dp))
        HomeContent(selectedBitmap = selectedBitmap,
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
fun UserDetail(userData: UserData?, signOut: () -> Unit) {
    var isContextMenuVisible by rememberSaveable { mutableStateOf(false) }
    var pressOffSet by remember { mutableStateOf(DpOffset(0.dp, 0.dp)) }
    var itemHeight by remember { mutableStateOf(0.dp) }
    var itemWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            "Welcome ${userData?.userName?.split(" ")?.first()}",
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(10.dp)
                .onSizeChanged {
                    itemHeight = with(density) { it.height.toDp() }
                    itemWidth = with(density) { it.width.toDp() }
                }, contentAlignment = Alignment.TopEnd
        ) {
            AsyncImage(model = userData?.profilePicUrl,
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { isContextMenuVisible = true })

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

@Composable
fun HomeContent(
    selectedBitmap: Uri?,
    launchImagePicker: () -> Unit,
    checkInternetStatus: () -> Unit
) {
    Box(
        modifier = Modifier.padding(16.dp), contentAlignment = Alignment.TopStart
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

            ImageUploads(selectedBitmap = selectedBitmap)
        }
    }
}

@Composable
fun ImageUploads(selectedBitmap: Uri?) {
    if (selectedBitmap != null) {
        val bitmap = ImageDecoder.decodeBitmap(
            ImageDecoder.createSource(
                LocalContext.current.contentResolver, selectedBitmap
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

