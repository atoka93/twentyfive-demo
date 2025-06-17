package com.attilaszabo.twentyfivedemo.quotepresentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attilaszabo.twentyfivedemo.commondomain.Result
import com.attilaszabo.twentyfivedemo.quotedomain.GetRandomQuoteUseCase
import com.attilaszabo.twentyfivedemo.quotedomain.Quote
import com.attilaszabo.twentyfivedeom.commonpresentation.DataState
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
                .catch { error ->
                    emit(Result.Error(error))
                }
                .collect { quoteResult ->
                    _dataState.value = when (quoteResult) {
                        is Result.Success -> DataState.Success(quoteResult.data)
                        is Result.Error ->
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
