package com.vkartik.myspace.ui.presentation.expenses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ExpensesScreen(modifier: Modifier = Modifier, viewModel: ExpensesViewModel = hiltViewModel()) {
    var promptText by rememberSaveable {
        mutableStateOf("")
    }
    val promptResponse: String? by viewModel.promptResponse.collectAsStateWithLifecycle()

    Box(modifier = modifier) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.SpaceEvenly
            ) {
                Card {
                    Text("Create an expense")
                }
                Card {
                    Text("Settings")
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            TextField(
                value = promptText, onValueChange = { promptText = it }, modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(48.dp)
            )

            if (!promptResponse.isNullOrBlank()) {
                Text(promptResponse!!)
            }
        }

        Button(
            onClick = {
                viewModel.obtainResponse(promptText)
                promptText = ""
            }, modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
        ) {
            Text(text = "Record")
        }
    }
}