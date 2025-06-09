package com.attilaszabo.twentyfivedemo.quotedomain

import kotlinx.coroutines.flow.Flow

interface QuoteRepository {
    fun obtainRandomQuote(): Flow<QuoteResponse>
}
