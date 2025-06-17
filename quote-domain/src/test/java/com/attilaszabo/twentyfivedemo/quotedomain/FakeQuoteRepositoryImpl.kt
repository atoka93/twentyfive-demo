package com.attilaszabo.twentyfivedemo.quotedomain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.attilaszabo.twentyfivedemo.commondomain.Result

class FakeQuoteRepositoryImpl(
    private val result: Result<Quote>
) : QuoteRepository {
    override fun obtainRandomQuote(): Flow<Result<Quote>> =
        flow {
            emit(result)
        }
}
