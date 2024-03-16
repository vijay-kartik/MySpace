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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.ui.extensions.showToast
import com.vkartik.myspace.ui.presentation.home.components.CreateCategoryCard
import com.vkartik.myspace.ui.presentation.home.components.DrawerContent
import com.vkartik.myspace.ui.presentation.home.components.ProfileIconButton
import com.vkartik.myspace.ui.utils.extractBorderColorFrom
import com.vkartik.myspace.ui.utils.resizeBitmap
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), navigateBackToSignIn: () -> Unit) {
    val homeUiState: HomeUiState? by viewModel.homeUiState.collectAsStateWithLifecycle()

    viewModel.fetchSignedInUserData()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        scrimColor = Color.Transparent,
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
                    ProfileIconButton(userData = homeUiState?.userData) {
                        viewModel.signOut { navigateBackToSignIn() }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors()
            )
        },
            content = { paddingValues ->
                HomeContent(
                    viewModel = viewModel,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        )
    }
}


@Composable
fun HomeContent(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val selectedBitmap: Uri? by viewModel.selectedImage.collectAsStateWithLifecycle()
    val internetConnected: Boolean? by viewModel.internetConnected.collectAsStateWithLifecycle()
    val homeUiState: HomeUiState? by viewModel.homeUiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
            viewModel.onImageSelected(uri)
        }
    Box(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(), contentAlignment = Alignment.TopStart
    ) {
        if (homeUiState?.showCategoryDialog == true) {
            CreateCategoryCard {
                viewModel.showCategoryDialog(false)
            }
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { viewModel.showCategoryDialog() }, modifier = Modifier.align(Alignment.End)) {
                Text(text = "Create a category")
            }
            Button(onClick = {
                internetConnected?.let { connected ->
                    context.showToast(if (connected) "Internet connected" else "Device Offline")
                }
            }) {
                Text(text = "Check internet status")
            }
            Button(onClick = {
                launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
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


