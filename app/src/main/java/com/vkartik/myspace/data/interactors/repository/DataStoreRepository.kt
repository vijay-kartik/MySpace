package com.vkartik.myspace.data.interactors.repository

import android.content.Context
import androidx.datastore.core.DataStore
import com.vkartik.myspace.UserStateProtoOuterClass.UserStateProto
import com.vkartik.myspace.domain.UserState
import javax.inject.Inject

class DataStoreRepository @Inject constructor(
    private val userStateStore: DataStore<UserStateProto>,
    val context: Context,
) {
    fun saveUserState(userState: UserState) {
        TODO()
    }
}