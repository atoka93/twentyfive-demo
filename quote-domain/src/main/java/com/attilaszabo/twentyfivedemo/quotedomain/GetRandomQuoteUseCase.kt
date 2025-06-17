package com.attilaszabo.twentyfivedemo.quotedomain

import kotlinx.coroutines.flow.Flow
import com.attilaszabo.twentyfivedemo.commondomain.Result

class GetRandomQuoteUseCase(
    private val quoteRepository: QuoteRepository,
) {
    fun execute(): Flow<Result<Quote>> {
        return quoteRepository.obtainRandomQuote()
    }
}
