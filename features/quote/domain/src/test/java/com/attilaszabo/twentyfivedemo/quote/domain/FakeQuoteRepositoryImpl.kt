package com.attilaszabo.twentyfivedemo.quote.domain

import com.attilaszabo.twentyfivedemo.common.domain.CustomResult
import com.attilaszabo.twentyfivedemo.quote.domain.models.Quote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeQuoteRepositoryImpl(
    private val result: CustomResult<Quote>
) : QuoteRepository {
    override fun obtainRandomQuote(): Flow<CustomResult<Quote>> =
        flow {
            emit(result)
        }
}
