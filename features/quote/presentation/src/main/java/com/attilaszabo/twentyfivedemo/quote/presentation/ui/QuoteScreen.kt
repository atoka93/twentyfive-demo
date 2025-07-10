package com.attilaszabo.twentyfivedemo.quote.presentation.ui


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.attilaszabo.twentyfivedemo.quote.domain.models.Quote
import com.attilaszabo.twentyfivedemo.quote.presentation.QuoteViewModel
import com.attilaszabo.twentyfivedemo.common.presentation.DataState
import com.attilaszabo.twentyfivedemo.common.presentation.theme.TwentyFiveDemoTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun QuoteScreen(
    onBackClick: () -> Unit,
    viewModel: QuoteViewModel = koinViewModel(),
) {
    val dataState = viewModel.dataState.collectAsStateWithLifecycle().value
    QuoteScreen(
        dataState = dataState,
        onBackClick = onBackClick,
        refreshQuote = viewModel::fetchRandomQuote,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuoteScreen(
    dataState: DataState<Quote>,
    onBackClick: () -> Unit,
    refreshQuote: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                title = {
                    Text("Random Quote")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = refreshQuote
            ) {
                Icon(
                    Icons.Filled.Refresh,
                    contentDescription = "Refresh"
                )
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(
                    mergePaddingValues(paddingValues, PaddingValues(all = 16.dp))
                )
            ) {
                when (dataState) {
                    is DataState.Initial -> {
                        Text("Welcome!\nFetching a quote in a second...")
                    }

                    is DataState.Loading -> {
                        CircularProgressIndicator()
                    }

                    is DataState.Success -> {
                        dataState.data.let {
                            Text(text = "\"${it.content}\"", style = MaterialTheme.typography.bodyLarge)
                            Spacer(Modifier.height(8.dp))
                            Text(text = "- ${it.author}", style = MaterialTheme.typography.bodySmall)
                        }
                    }

                    is DataState.Error -> {
                        Text("Error: ${dataState.message}")
                    }

                    is DataState.Empty -> {
                        Text("No quote found.")
                    }
                }
            }
        }
    )
}

@Composable
fun mergePaddingValues(p1: PaddingValues, p2: PaddingValues): PaddingValues {
    return PaddingValues(
        start = p1.calculateStartPadding(layoutDirection = LayoutDirection.Ltr) +
                p2.calculateStartPadding(layoutDirection = LayoutDirection.Ltr),
        top = p1.calculateTopPadding() + p2.calculateTopPadding(),
        end = p1.calculateEndPadding(layoutDirection = LayoutDirection.Ltr) +
                p2.calculateEndPadding(layoutDirection = LayoutDirection.Ltr),
        bottom = p1.calculateBottomPadding() + p2.calculateBottomPadding()
    )
}

@Preview(showBackground = true)
@Composable
fun QuoteScreenPreview() {
    val fakeDataState: DataState<Quote> by remember {
        mutableStateOf(
            DataState.Success(
                Quote(
                    id = "id",
                    content = "To be or not to be, that is the question.",
                    author = "William Shakespeare",
                    tags = listOf(),
                )
            )
        )
    }

    TwentyFiveDemoTheme {
        QuoteScreen(
            dataState = fakeDataState,
            onBackClick = {},
            refreshQuote = {}
        )
    }
}
