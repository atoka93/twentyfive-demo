package com.attilaszabo.twentyfivedemo.quote.domain

import com.attilaszabo.twentyfivedemo.common.domain.CustomResult
import com.attilaszabo.twentyfivedemo.quote.domain.models.Quote
import kotlinx.coroutines.flow.Flow

class GetRandomQuoteUseCase(
    private val quoteRepository: QuoteRepository,
) {
    fun execute(): Flow<CustomResult<Quote>> {
        return quoteRepository.obtainRandomQuote()
    }
}
