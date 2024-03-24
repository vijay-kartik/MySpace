package com.vkartik.myspace.ui.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vkartik.myspace.ui.presentation.home.components.CategoryCard
import com.vkartik.myspace.ui.presentation.home.components.CreateCategoryDialog
import com.vkartik.myspace.ui.presentation.home.components.DrawerContent
import com.vkartik.myspace.ui.presentation.home.components.ProfileIconButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(coroutineScope: CoroutineScope, viewModel: HomeViewModel = hiltViewModel(), navigateBackToSignIn: () -> Unit) {
    val homeUiState: HomeUiState? by viewModel.homeUiState.collectAsStateWithLifecycle()

    viewModel.fetchSignedInUserData()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

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
    val homeUiState: HomeUiState? by viewModel.homeUiState.collectAsStateWithLifecycle()
    Box(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(), contentAlignment = Alignment.TopStart
    ) {
        if (homeUiState?.showCategoryDialog == true) {
            CreateCategoryDialog(viewModel = viewModel) {
                viewModel.showCategoryDialog(false)
            }
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { viewModel.showCategoryDialog() }, modifier = Modifier.align(Alignment.End)) {
                Text(text = "Create a category")
            }

            CategoryList(homeUiState)
        }
    }
}

@Composable
fun CategoryList(homeUiState: HomeUiState?) {
    if (homeUiState?.categoryList?.isNotEmpty() == true) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 130.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(homeUiState.categoryList.size) { index ->
                CategoryCard(homeUiState.categoryList[index])
            }
        }
    }
}


