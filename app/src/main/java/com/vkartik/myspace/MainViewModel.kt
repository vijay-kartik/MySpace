package com.vkartik.myspace

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class MainViewModel @Inject constructor(): ViewModel() {
    val _netWorkState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val networkState = _netWorkState.asStateFlow()


    suspend fun checkIsDeviceOffline() {
        
    }
}