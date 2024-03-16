package com.vkartik.myspace.ui.presentation.home

import android.net.ConnectivityManager.NetworkCallback
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vkartik.myspace.data.GoogleAuthUiClient
import com.vkartik.myspace.data.interactors.CheckInternetStatusUseCase
import com.vkartik.myspace.data.interactors.UploadFileUseCase
import com.vkartik.myspace.domain.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val uploadFileUseCase: UploadFileUseCase,
    private val checkInternetStatusUseCase: CheckInternetStatusUseCase,
    private val googleAuthUiClient: GoogleAuthUiClient
): ViewModel() {
    init {
        checkInternetStatus()
    }
    private val _fileSelected: MutableStateFlow<String?> = MutableStateFlow(null)
    val fileSelected: StateFlow<String?> get() = _fileSelected.asStateFlow()

    private val _internetConnected: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val internetConnected: StateFlow<Boolean?> get() = _internetConnected

    private val _selectedImage: MutableStateFlow<Uri?> = MutableStateFlow(null)
    val selectedImage: StateFlow<Uri?> get() = _selectedImage

    private val _homeUiState: MutableStateFlow<HomeUiState?> = MutableStateFlow(null)
    val homeUiState: StateFlow<HomeUiState?> get() = _homeUiState

    fun onFileSelected(fileUri: String?) {
        _fileSelected.value = fileUri
        uploadFileUseCase.execute(fileUri)
    }

    private fun checkInternetStatus() {
        checkInternetStatusUseCase.execute(object: NetworkCallback() {
            override fun onAvailable(network: android.net.Network) {
                _internetConnected.value = true
            }

            override fun onLost(network: android.net.Network) {
                _internetConnected.value = false
            }

            override fun onUnavailable() {
                _internetConnected.value = false
            }
        })
    }

    fun onImageSelected(uri: Uri?) {
        _selectedImage.value = uri
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
        _homeUiState.update {
            it?.categoryList?.add(Category(categoryName, selectedImageUri))
            it
        }
    }
}
