package com.attilaszabo.twentyfivedemo.quoteapi

import retrofit2.http.GET

interface QuotableApiService {
    @GET("random")
    suspend fun getRandomQuote(): QuoteResponseDto
}
