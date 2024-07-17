package com.vkartik.myspace.ui.presentation.expenses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun RecordTransactionScreen(modifier: Modifier = Modifier, viewModel: RecordTransactionViewModel = hiltViewModel()) {
    var promptText by rememberSaveable {
        mutableStateOf("")
    }
    val promptResponse: String? by viewModel.promptResponse.collectAsStateWithLifecycle()

    Box(modifier = modifier.padding(10.dp)) {
        Column {
            TextField(
                value = promptText, onValueChange = { promptText = it }, modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .align(Alignment.CenterHorizontally),
                label = { Text("Input") }, placeholder = { Text("Enter a transaction SMS") }
            )
            if (!promptResponse.isNullOrBlank()) {
                Text(promptResponse!!)
            }
            Text(text = "OR", modifier = Modifier
                .padding(vertical = 20.dp)
                .align(Alignment.CenterHorizontally))
            OutlinedCard(modifier = Modifier.fillMaxWidth(0.7f).align(Alignment.CenterHorizontally).height(100.dp)) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Drag or browse file to attach", textAlign = TextAlign.Center)
                }
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

@Composable
@Preview
fun RecordTransactionScreenPreview() {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)) {
        Column(Modifier.fillMaxWidth()) {
            TextField(
                value = "Enter a transaction SMS", onValueChange = { }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.7f)
                    .align(Alignment.CenterHorizontally),
                label = { Text("Input") }, placeholder = { Text("Enter a transaction SMS") }
            )
            Text(text = "OR", modifier = Modifier
                .padding(vertical = 20.dp)
                .align(Alignment.CenterHorizontally))
            OutlinedCard(modifier = Modifier.fillMaxWidth(0.7f).align(Alignment.CenterHorizontally).height(100.dp)) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Drag or browse file to attach", textAlign = TextAlign.Center)
                }
            }
        }

        Button(
            onClick = {

            }, modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
        ) {
            Text(text = "Record")
        }
    }
}