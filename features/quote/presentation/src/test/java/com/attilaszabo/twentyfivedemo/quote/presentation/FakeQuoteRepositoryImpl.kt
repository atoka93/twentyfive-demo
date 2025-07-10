package com.attilaszabo.twentyfivedemo.quote.presentation

import com.attilaszabo.twentyfivedemo.common.domain.CustomResult
import com.attilaszabo.twentyfivedemo.quote.domain.models.Quote
import com.attilaszabo.twentyfivedemo.quote.domain.QuoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeQuoteRepositoryImpl : QuoteRepository {
    val flow = MutableSharedFlow<CustomResult<Quote>>(
        replay = Int.MAX_VALUE
    )

    override fun obtainRandomQuote(): Flow<CustomResult<Quote>> = flow
}
