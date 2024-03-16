package com.vkartik.myspace

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.datastore.dataStore
import com.vkartik.myspace.data.UserStateProtoSerializer
import com.vkartik.myspace.ui.navigation.MySpaceNavHost
import com.vkartik.myspace.ui.theme.MySpaceTheme
import dagger.hilt.android.AndroidEntryPoint

private val Context.userStateStore by dataStore(fileName = "UserStateProto.proto", serializer = UserStateProtoSerializer)


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MySpaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    MySpaceNavHost()
                }
            }
        }
    }
}