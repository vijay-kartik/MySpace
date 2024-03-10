package com.vkartik.myspace.ui.presentation.home

import android.net.ConnectivityManager.NetworkCallback
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.vkartik.myspace.data.interactors.CheckInternetStatusUseCase
import com.vkartik.myspace.data.interactors.UploadFileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val uploadFileUseCase: UploadFileUseCase,
    private val checkInternetStatusUseCase: CheckInternetStatusUseCase
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
}
