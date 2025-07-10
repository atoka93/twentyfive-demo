package com.attilaszabo.twentyfivedemo.quoteapi

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class QuoteRemoteDataSourceImplTest {
    @Test
    fun `fetchRandomQuote returns quote`() = runTest {
        // Setup
        val fakeQuoteResponseDto = QuoteResponseDto(
            "id",
            "This is a test quote.",
            "Test Author",
            "test-author",
            21,
            listOf()
        )
        val fakeApiService = FakeQuotableApiService(fakeQuoteResponseDto)

        val dataSource = QuoteRemoteDataSourceImpl(fakeApiService)

        // Execution
        val result = dataSource.fetchRandomQuote()

        // Assertion
        result.collect { quote ->
            assertEquals(quote, fakeQuoteResponseDto)
        }
    }

    @Test
    fun `fetchRandomQuote throws exception when data is not available`() = runTest {
        // Setup
        val fakeApiService = FakeQuotableApiService(null)

        val dataSource = QuoteRemoteDataSourceImpl(fakeApiService)

        // Execution
        val result = dataSource.fetchRandomQuote()

        // Assertion
        result.catch { cause ->
            assertTrue(cause is Exception)
            assertEquals(cause.message, FakeQuotableApiService.ERROR)
        }
    }
}
