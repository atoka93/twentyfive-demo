package com.attilaszabo.twentyfivedemo.quotedata

import com.attilaszabo.twentyfivedemo.quoteapi.QuoteRemoteDataSource
import com.attilaszabo.twentyfivedemo.quotedomain.QuoteRepository
import com.attilaszabo.twentyfivedemo.quotedomain.QuoteResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(
    private val remoteDataSource: QuoteRemoteDataSource
) : QuoteRepository {

    override fun obtainRandomQuote(): Flow<QuoteResponse> {
        return remoteDataSource.fetchRandomQuote().map(QuoteResponseMapper::mapToDomain)
    }
}
