package com.attilaszabo.twentyfivedemo.quotedata

import com.attilaszabo.twentyfivedemo.quoteapi.QuoteRemoteDataSource
import com.attilaszabo.twentyfivedemo.quoteapi.QuoteResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeQuoteRemoteDataSourceImpl(
    private val quoteResponse: QuoteResponseDto?
) : QuoteRemoteDataSource {
    override fun fetchRandomQuote(): Flow<QuoteResponseDto?> =
        flow {
            emit(quoteResponse)
        }
}
