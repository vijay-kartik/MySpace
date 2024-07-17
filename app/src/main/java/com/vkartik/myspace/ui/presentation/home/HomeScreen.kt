package com.vkartik.myspace.ui.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vkartik.myspace.MySpaceAppState
import com.vkartik.myspace.ui.presentation.SubScreens
import com.vkartik.myspace.ui.presentation.expenses.ExpensesScreen
import com.vkartik.myspace.ui.presentation.expenses.RecordTransactionScreen
import com.vkartik.myspace.ui.presentation.home.components.CategoryCard
import com.vkartik.myspace.ui.presentation.home.components.CreateCategoryDialog
import com.vkartik.myspace.ui.presentation.home.components.DrawerContent
import com.vkartik.myspace.ui.presentation.home.components.ProfileIconButton
import com.vkartik.myspace.ui.presentation.sign_in.UserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    appState: MySpaceAppState,
    viewModel: HomeViewModel = hiltViewModel(),
    newUser: Boolean = false,
    navigateBackToSignIn: () -> Unit
) {
    val userData: UserData? by viewModel.userData.collectAsStateWithLifecycle()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val homeUiState: HomeUiState? by viewModel.homeUiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.fetchSignedInUserData()
        viewModel.fetchCategoriesList(newUser)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        scrimColor = Color.Transparent,
        drawerContent = {
            DrawerContent { subScreen ->
                viewModel.onDrawerItemClicked(subScreen)
                appState.coroutineScope.launch {
                    drawerState.close()
                }
            }
        }) {
        Scaffold(topBar = {
            TopAppBar(
                title = { Text(homeUiState?.currentScreen?.name ?: "My Space") },
                navigationIcon = {
                    IconButton(onClick = {
                        appState.coroutineScope.launch {
                            drawerState.open()
                        }
                    }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    ProfileIconButton(userData = userData) {
                        viewModel.signOut { navigateBackToSignIn() }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors()
            )
        },
            content = { paddingValues ->
                HomeContent(
                    viewModel = viewModel,
                    modifier = Modifier.padding(paddingValues),
                    appState = appState
                )
            }
        )
    }
}


@Composable
fun HomeContent(
    appState: MySpaceAppState,
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val homeUiState: HomeUiState? by viewModel.homeUiState.collectAsStateWithLifecycle()
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {

        when (homeUiState?.currentScreen) {
            SubScreens.DEFAULT -> {
                if (homeUiState?.showCategoryDialog == true) {
                    CreateCategoryDialog(viewModel = viewModel) {
                        viewModel.showCategoryDialog(false)
                    }
                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { viewModel.showCategoryDialog() },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(text = "Create a category")
                    }
                    CategoryList(homeUiState, viewModel, appState.coroutineScope)
                }
            }

            SubScreens.EXPENSES -> {
                ExpensesScreen(modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp)) {
                    appState.navigate(SubScreens.RECORD_TRANSACTIONS.route)
                }
            }

            else -> {}
        }
    }
}

@Composable
fun CategoryList(
    homeUiState: HomeUiState?,
    viewModel: HomeViewModel,
    coroutineScope: CoroutineScope
) {
    if (homeUiState?.categoryList?.isNotEmpty() == true) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 130.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(homeUiState.categoryList.size) { index ->
                CategoryCard(homeUiState.categoryList[index], coroutineScope, viewModel)
            }
        }
    }
}


