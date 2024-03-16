package com.vkartik.myspace.ui.presentation.home

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.core.ui.extensions.showToast
import com.vkartik.myspace.ui.utils.extractBorderColorFrom
import com.vkartik.myspace.ui.utils.resizeBitmap
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), navigateBackToSignIn: () -> Unit) {
    val homeUiState: HomeUiState? by viewModel.homeUiState.collectAsStateWithLifecycle()
    val internetConnected: Boolean? by viewModel.internetConnected.collectAsStateWithLifecycle()
    val selectedBitmap: Uri? by viewModel.selectedImage.collectAsStateWithLifecycle()

    viewModel.fetchSignedInUserData()
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
            viewModel.onImageSelected(uri)
        }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { DrawerContent() }) {
        Scaffold(topBar = {
            TopAppBar(
                title = { Text("My Space") },
                navigationIcon = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            drawerState.open()
                        }
                    }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    ProfileIconButton(homeUiState = homeUiState) {
                        viewModel.signOut { navigateBackToSignIn() }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors()
            )
        },
            content = { paddingValues ->
                HomeContent(
                    selectedBitmap = selectedBitmap,
                    launchImagePicker = {
                        launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    },
                    checkInternetStatus = {
                        internetConnected?.let { connected ->
                            context.showToast(if (connected) "Internet connected" else "Device Offline")
                        }
                    },
                    modifier = Modifier.padding(paddingValues)
                )
            }
        )
    }
}


@Composable
fun ProfileIconButton(homeUiState: HomeUiState?, signOut: () -> Unit) {
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
                model = homeUiState?.userData?.profilePicUrl,
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


@Composable
fun HomeContent(
    selectedBitmap: Uri?,
    launchImagePicker: () -> Unit,
    checkInternetStatus: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.padding(16.dp), contentAlignment = Alignment.TopStart
    ) {
        Column {
            Spacer(modifier = Modifier.padding(8.dp))
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

@Composable
fun DrawerContent() {
    Column {
        Text(text = "Movies")
        Text(text = "Fodd log")
    }
}

