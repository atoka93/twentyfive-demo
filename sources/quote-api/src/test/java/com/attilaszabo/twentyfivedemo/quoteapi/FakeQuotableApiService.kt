package com.attilaszabo.twentyfivedemo.quoteapi

class FakeQuotableApiService(
    private val quoteResponseDto: QuoteResponseDto?
) : QuotableApiService {
    companion object {
        const val ERROR = "No quote available"
    }

    override suspend fun getRandomQuote(): QuoteResponseDto =
        quoteResponseDto ?: throw Exception(ERROR)
}
