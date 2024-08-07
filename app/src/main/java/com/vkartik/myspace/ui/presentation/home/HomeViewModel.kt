package com.vkartik.myspace.ui.presentation.home

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.Category
import com.vkartik.myspace.data.GoogleAuthUiClient
import com.vkartik.myspace.data.interactors.CreateCategoryUseCase
import com.vkartik.myspace.data.interactors.GetCategoryImageUseCase
import com.vkartik.myspace.data.interactors.UploadFileUseCase
import com.vkartik.myspace.domain.ClearUserDataUseCase
import com.vkartik.myspace.domain.GetCategoriesUseCase
import com.vkartik.myspace.ui.presentation.SubScreens
import com.vkartik.myspace.ui.presentation.sign_in.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val uploadFileUseCase: UploadFileUseCase,
    private val createCategoryUseCase: CreateCategoryUseCase,
    private val googleAuthUiClient: GoogleAuthUiClient,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getCategoryImageUseCase: GetCategoryImageUseCase,
    private val clearUserDataUseCase: ClearUserDataUseCase
) : ViewModel() {
    private val _homeUiState: MutableStateFlow<HomeUiState?> = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState?> get() = _homeUiState

    private val _userData: MutableStateFlow<UserData?> = MutableStateFlow(null)
    val userData: StateFlow<UserData?> get() = _userData

    private val _startProgress = MutableStateFlow(false)
    val startProgress: StateFlow<Boolean> get() = _startProgress

    fun onDrawerItemClicked(subScreen: SubScreens) {
        _homeUiState.update { it?.copy(currentScreen = subScreen) }
    }

    fun fetchSignedInUserData() {
        googleAuthUiClient.getSignedInUser()?.let {
            _userData.value = it
        }
    }

    fun signOut(onSuccessfulSignOut: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            googleAuthUiClient.signOut()
            clearUserDataUseCase.execute()
            withContext(Dispatchers.Main) {
                onSuccessfulSignOut()
            }
        }
    }

    fun showCategoryDialog(show: Boolean = true) {
        _homeUiState.update {
            it?.copy(showCategoryDialog = show)
        }
    }

    fun createCategory(selectedImageUri: Uri?, categoryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _startProgress.value = true
            val storagePath = uploadFileUseCase.execute(selectedImageUri)
            val category =
                Category(uid = "cat" + "_" + System.currentTimeMillis(), categoryName, storagePath)
            val created = createCategoryUseCase.execute(category)
            if (created) {
                _startProgress.value = false
                _homeUiState.update { oldState ->
                    val newList = oldState?.categoryList?.toMutableList()
                    newList?.run {
                        add(category)
                        oldState.copy(categoryList = this)
                    }
                }
                showCategoryDialog(false)
            }
        }
    }

    suspend fun getCategoryImage(storagePath: String): String? {
        return getCategoryImageUseCase.execute(storagePath)
    }

    fun fetchCategoriesList(newUser: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val categoriesList = getCategoriesUseCase.execute(newUser)
            categoriesList.collectLatest { categories ->
                _homeUiState.update {
                    it?.copy(categoryList = categories)
                }
            }
        }
    }
}
