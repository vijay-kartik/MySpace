package com.vkartik.myspace.ui.presentation.expenses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ExpensesScreen(modifier: Modifier = Modifier, viewModel: ExpensesViewModel = hiltViewModel()) {
    Box(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Absolute.SpaceEvenly) {
            Card {
                Text("Create an expense")
            }
            Card {
                Text("Settings")
            }
        }

        Button(onClick = { /*TODO*/ }, modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(20.dp)) {
            Text(text = "Record")
        }
    }
}