package com.attilaszabo.twentyfivedemo.quotepresentation

import com.attilaszabo.twentyfivedemo.commondomain.Result
import com.attilaszabo.twentyfivedemo.quotedomain.Quote
import com.attilaszabo.twentyfivedemo.quotedomain.QuoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeQuoteRepositoryImpl : QuoteRepository {
    val flow = MutableSharedFlow<Result<Quote>>(
        replay = Int.MAX_VALUE
    )

    override fun obtainRandomQuote(): Flow<Result<Quote>> = flow
}
