package com.attilaszabo.twentyfivedemo.quotedomain

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import com.attilaszabo.twentyfivedemo.commondomain.Result

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
        val fakeQuoteRepository = FakeQuoteRepositoryImpl(Result.Success(fakeQuote))

        val getRandomQuoteUseCase = GetRandomQuoteUseCase(fakeQuoteRepository)

        // Execution
        val result = getRandomQuoteUseCase.execute()

        // Assertion
        result.collect {
            assertEquals(it, Result.Success(fakeQuote))
        }
    }

    @Test
    fun `execute returns result error with null if no quote is found`() = runBlocking {
        // Setup
        val fakeQuoteRepository = FakeQuoteRepositoryImpl(Result.Error(null))

        val getRandomQuoteUseCase = GetRandomQuoteUseCase(fakeQuoteRepository)

        // Execution
        val result = getRandomQuoteUseCase.execute()

        // Assertion
        result.collect {
            assertEquals(it, Result.Error<Quote>())
        }
    }
}
