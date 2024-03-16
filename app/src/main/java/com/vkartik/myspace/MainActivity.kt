package com.vkartik.myspace

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.datastore.dataStore
import com.vkartik.myspace.data.UserStateProtoSerializer
import dagger.hilt.android.AndroidEntryPoint

private val Context.userStateStore by dataStore(fileName = "UserStateProto.proto", serializer = UserStateProtoSerializer)


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MySpaceApp()
        }
    }
}