package com.attilaszabo.twentyfivedemo.quotepresentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attilaszabo.twentyfivedemo.quotedomain.GetRandomQuoteUseCase
import com.attilaszabo.twentyfivedemo.quotedomain.QuoteResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val getRandomQuoteUseCase: GetRandomQuoteUseCase
) : ViewModel() {

    private val _quote = MutableStateFlow<QuoteResponse?>(null)
    val quote: StateFlow<QuoteResponse?> = _quote

    init {
        fetchRandomQuote()
    }

    fun fetchRandomQuote() {
        viewModelScope.launch {
            getRandomQuoteUseCase.execute().collect { quoteResponse ->
                _quote.value = quoteResponse
            }
        }
    }
}
