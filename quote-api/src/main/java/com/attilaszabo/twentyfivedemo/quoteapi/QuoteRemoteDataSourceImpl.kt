package com.attilaszabo.twentyfivedemo.quoteapi

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class QuoteRemoteDataSourceImpl(
    private val api: QuotableApiService,
) : QuoteRemoteDataSource {

    override fun fetchRandomQuote(): Flow<QuoteResponseDto> = flow {
        val quote = api.getRandomQuote()
        emit(quote)
    }
}
