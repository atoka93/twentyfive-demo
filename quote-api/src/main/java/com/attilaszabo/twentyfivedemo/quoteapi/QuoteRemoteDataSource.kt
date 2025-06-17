package com.attilaszabo.twentyfivedemo.quoteapi

import kotlinx.coroutines.flow.Flow

interface QuoteRemoteDataSource {
    fun fetchRandomQuote(): Flow<QuoteResponseDto?>
}
