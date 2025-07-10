package com.attilaszabo.twentyfivedemo.quote.domain

import com.attilaszabo.twentyfivedemo.common.domain.CustomResult
import com.attilaszabo.twentyfivedemo.quote.domain.models.Quote
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetRandomQuoteUseCaseTest {
    @Test
    fun `execute returns result success with quote`() = runTest {
        // Setup
        val fakeQuote = Quote(
            "id",
            "This is a test quote.",
            "Test Author",
            listOf()
        )
        val fakeQuoteRepository = FakeQuoteRepositoryImpl(CustomResult.success(fakeQuote))

        val getRandomQuoteUseCase = GetRandomQuoteUseCase(fakeQuoteRepository)

        // Execution
        val result = getRandomQuoteUseCase.execute()

        // Assertion
        result.collect {
            assertEquals(it, CustomResult.success(fakeQuote))
        }
    }

    @Test
    fun `execute returns result error with null if no quote is found`() = runBlocking {
        // Setup
        val fakeQuoteRepository = FakeQuoteRepositoryImpl(CustomResult.failure(null))

        val getRandomQuoteUseCase = GetRandomQuoteUseCase(fakeQuoteRepository)

        // Execution
        val result = getRandomQuoteUseCase.execute()

        // Assertion
        result.collect {
            assertEquals(it, CustomResult.failure<Quote>())
        }
    }
}
