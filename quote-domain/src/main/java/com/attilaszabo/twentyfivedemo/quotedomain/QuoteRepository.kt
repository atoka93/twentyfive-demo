package com.attilaszabo.twentyfivedemo.quotedomain

import kotlinx.coroutines.flow.Flow
import com.attilaszabo.twentyfivedemo.commondomain.Result

interface QuoteRepository {
    fun obtainRandomQuote(): Flow<Result<Quote>>
}
