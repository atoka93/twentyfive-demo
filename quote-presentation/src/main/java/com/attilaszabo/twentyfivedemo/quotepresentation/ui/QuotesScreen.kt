package com.attilaszabo.twentyfivedemo.quotepresentation.ui


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.unit.dp
import com.attilaszabo.twentyfivedemo.quotedomain.QuoteResponse
import com.attilaszabo.twentyfivedemo.quotepresentation.QuoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuotesScreen(viewModel: QuoteViewModel = hiltViewModel()) {
    val quote = viewModel.quote.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Scaffold Example") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                Button(onClick = { viewModel.fetchRandomQuote() }) {
                    Text(text = "Get New Quote")
                }
                quote?.let {
                    Text(text = "\"${it.content}\"", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "- ${it.author}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewQuoteApp() {
    val dummyQuote = QuoteResponse(
        id = "1",
        content = "To be or not to be, that is the question.",
        author = "William Shakespeare",
        tags = listOf("life", "philosophy")
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "\"${dummyQuote.content}\"", style = MaterialTheme.typography.bodyLarge)
        Text(text = "- ${dummyQuote.author}", style = MaterialTheme.typography.bodySmall)
    }
}

