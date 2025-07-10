package com.attilaszabo.twentyfivedemo.quote.domain

import com.attilaszabo.twentyfivedemo.common.domain.CustomResult
import com.attilaszabo.twentyfivedemo.quote.domain.models.Quote
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {
    fun obtainRandomQuote(): Flow<CustomResult<Quote>>
}
