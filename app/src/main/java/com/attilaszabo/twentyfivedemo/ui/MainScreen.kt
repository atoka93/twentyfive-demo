package com.attilaszabo.twentyfivedemo.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainScreen(navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        // Content for MainActivity
        Button(
            onClick = {
                // Navigate to the QuotesActivity screen when button is clicked
                navController.navigate("quotes")
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Go to Quotes")
        }
    }
}
