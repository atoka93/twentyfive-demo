package com.attilaszabo.twentyfivedemo.quotedomain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRandomQuoteUseCase @Inject constructor(
    private val quoteRepository: QuoteRepository,
) {
    fun execute(): Flow<QuoteResponse> {
        return quoteRepository.obtainRandomQuote()
    }
}
