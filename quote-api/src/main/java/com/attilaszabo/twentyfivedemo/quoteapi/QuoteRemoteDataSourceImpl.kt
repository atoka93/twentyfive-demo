package com.attilaszabo.twentyfivedemo.quoteapi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class QuoteRemoteDataSourceImpl @Inject constructor(
    private val api: QuotableApiService
) : QuoteRemoteDataSource {

    override fun fetchRandomQuote(): Flow<QuoteResponseDto> = flow {
        val quote = api.getRandomQuote()
        emit(quote)
    }
}
