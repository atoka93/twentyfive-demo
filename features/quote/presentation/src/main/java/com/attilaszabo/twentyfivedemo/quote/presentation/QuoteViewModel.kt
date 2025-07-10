package com.attilaszabo.twentyfivedemo.quote.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attilaszabo.twentyfivedemo.common.domain.CustomResult
import com.attilaszabo.twentyfivedemo.quote.domain.GetRandomQuoteUseCase
import com.attilaszabo.twentyfivedemo.quote.domain.models.Quote
import com.attilaszabo.twentyfivedemo.common.presentation.DataState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class QuoteViewModel(
    private val getRandomQuoteUseCase: GetRandomQuoteUseCase
) : ViewModel() {

    private val _dataState = MutableStateFlow<DataState<Quote>>(DataState.Initial)
    val dataState: StateFlow<DataState<Quote>> = _dataState

    private var _fetchJob: Job? = null

    init {
        fetchRandomQuote()
    }

    fun fetchRandomQuote() {
        _fetchJob?.cancel()
        _fetchJob = viewModelScope.launch {
            getRandomQuoteUseCase
                .execute()
                .onStart {
                    _dataState.value = DataState.Loading
                }
                .catch { cause ->
                    emit(CustomResult.failure(cause))
                }
                .collect { quoteResult ->
                    _dataState.value = when (quoteResult) {
                        is CustomResult.success -> DataState.Success(quoteResult.data)
                        is CustomResult.failure ->
                            if (quoteResult.exception == null) {
                                DataState.Empty
                            } else {
                                DataState.Error(
                                    quoteResult.exception.toString()
                                )
                            }
                    }
                }
        }
    }
}
