package com.vkartik.myspace.ui.presentation.home

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vkartik.myspace.data.GoogleAuthUiClient
import com.vkartik.myspace.data.interactors.CreateCategoryUseCase
import com.vkartik.myspace.data.interactors.UploadFileUseCase
import com.vkartik.myspace.domain.Category
import com.vkartik.myspace.domain.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val uploadFileUseCase: UploadFileUseCase,
    private val createCategoryUseCase: CreateCategoryUseCase,
    private val googleAuthUiClient: GoogleAuthUiClient,
    private val getCategoriesUseCase: GetCategoriesUseCase
): ViewModel() {
    private val _homeUiState: MutableStateFlow<HomeUiState?> = MutableStateFlow(null)
    val homeUiState: StateFlow<HomeUiState?> get() = _homeUiState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val categories = getCategoriesUseCase.execute()
            _homeUiState.update {
                it?.copy(categoryList = categories.toMutableList())
            }
        }
    }

    fun fetchSignedInUserData() {
        googleAuthUiClient.getSignedInUser()?.let {
            _homeUiState.value = HomeUiState(it)
        }
    }

    fun signOut(onSuccessfulSignOut: () -> Unit) {
        viewModelScope.launch {
            googleAuthUiClient.signOut()
            onSuccessfulSignOut()
        }
    }

    fun showCategoryDialog(show: Boolean = true) {
        _homeUiState.update {
            it?.copy(showCategoryDialog = show)
        }
    }

    fun onCategoryCreated(selectedImageUri: Uri?, categoryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val storagePath = uploadFileUseCase.execute(selectedImageUri?.toString())
            val category = Category(categoryName, selectedImageUri, storagePath)
            val created = createCategoryUseCase.execute(category)
            if (created) {
                _homeUiState.update {oldState ->
                    val newList = oldState?.categoryList?.toMutableList()
                    newList?.run {
                        add(category)
                        oldState.copy(categoryList = this)
                    }
                }
            }
        }
    }


}
