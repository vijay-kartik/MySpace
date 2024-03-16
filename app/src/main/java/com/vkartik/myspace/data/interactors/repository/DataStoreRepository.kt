package com.vkartik.myspace.data.interactors.repository

import android.content.Context
import androidx.datastore.core.DataStore
import com.vkartik.myspace.domain.UserState
import javax.inject.Inject

class DataStoreRepository @Inject constructor(
    val context: Context,
) {
    fun saveUserState(userState: UserState) {
        TODO()
    }
}