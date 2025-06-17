package com.attilaszabo.twentyfivedemo.quotedata

import com.attilaszabo.twentyfivedemo.quotecache.QuoteLocalDataSource
import com.attilaszabo.twentyfivedemo.quotecache.QuoteLocalDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeQuoteLocalDataSourceImpl(
    private val outputQuote: QuoteLocalDto?
) : QuoteLocalDataSource {

    var inputQuote: QuoteLocalDto? = null

    override val quoteFlow: Flow<QuoteLocalDto> =
        flow {
            if (outputQuote != null) {
                emit(outputQuote)
            }
        }

    override suspend fun saveQuote(quote: QuoteLocalDto) {
        inputQuote = quote
    }
}
