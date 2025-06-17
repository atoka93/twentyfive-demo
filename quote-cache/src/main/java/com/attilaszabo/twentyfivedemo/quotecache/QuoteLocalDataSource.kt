package com.attilaszabo.twentyfivedemo.quotecache

import kotlinx.coroutines.flow.Flow

interface QuoteLocalDataSource {
    val quoteFlow: Flow<QuoteLocalDto>

    suspend fun saveQuote(quote: QuoteLocalDto)
}
