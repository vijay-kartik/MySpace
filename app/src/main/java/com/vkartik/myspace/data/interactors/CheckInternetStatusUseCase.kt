package com.vkartik.myspace.data.interactors

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.NetworkRequest
import javax.inject.Inject

class CheckInternetStatusUseCase @Inject constructor(
    private val networkRequest: NetworkRequest,
    private val context: Context
) {
    fun execute(networkCallback: NetworkCallback) {
        val connectivityManager = context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }
}