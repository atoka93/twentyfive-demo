package com.attilaszabo.twentyfivedemo.quotedata

import com.attilaszabo.twentyfivedemo.commondomain.Result
import com.attilaszabo.twentyfivedemo.quoteapi.QuoteResponseDto
import com.attilaszabo.twentyfivedemo.quotecache.QuoteLocalDto
import com.attilaszabo.twentyfivedemo.quotedomain.Quote
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class QuoteRepositoryImplTest {
    @Test
    fun `obtainRandomQuote is executed on the correct dispatcher`() = runTest {
        // Setup
        val fakeQuoteLocalDto = QuoteLocalDto(
            "id-local",
            "This is a local test quote.",
            "Local Test Author",
            listOf()
        )
        val fakeQuoteResponseDto = QuoteResponseDto(
            "id",
            "This is a test quote.",
            "Test Author",
            "test-author",
            21,
            listOf()
        )
        val fakeQuoteRemoteDataSource = FakeQuoteRemoteDataSourceImpl(fakeQuoteResponseDto)
        val fakeQuoteLocalDataSource = FakeQuoteLocalDataSourceImpl(fakeQuoteLocalDto)
        val dispatcher = StandardTestDispatcher(testScheduler)
        val scope = TestScope(dispatcher)

        val quoteRepository = QuoteRepositoryImpl(fakeQuoteRemoteDataSource, fakeQuoteLocalDataSource, dispatcher)

        // Execution & Assertion
        quoteRepository.obtainRandomQuote().collect {
            assertTrue(dispatcher.isDispatchNeeded(scope.coroutineContext))
        }

    }

    @Test
    fun `obtainRandomQuote returns local then remote quote`() = runTest {
        // Setup
        val fakeQuoteLocalDto = QuoteLocalDto(
            "id-local",
            "This is a local test quote.",
            "Local Test Author",
            listOf()
        )
        val fakeQuoteResponseDto = QuoteResponseDto(
            "id",
            "This is a test quote.",
            "Test Author",
            "test-author",
            21,
            listOf()
        )
        val fakeQuoteRemoteDataSource = FakeQuoteRemoteDataSourceImpl(fakeQuoteResponseDto)
        val fakeQuoteLocalDataSource = FakeQuoteLocalDataSourceImpl(fakeQuoteLocalDto)
        val dispatcher = StandardTestDispatcher(testScheduler)

        val quoteRepository = QuoteRepositoryImpl(fakeQuoteRemoteDataSource, fakeQuoteLocalDataSource, dispatcher)

        // Execution
        val results = mutableListOf<Result<Quote>>()
        quoteRepository.obtainRandomQuote().toCollection(results)

        // Assertion
        assertEquals(
            results,
            listOf(
                Result.Success(
                    Quote(
                        "id-local",
                        "This is a local test quote.",
                        "Local Test Author",
                        listOf()
                    ),
                ),
                Result.Success(
                    Quote(
                        "id",
                        "This is a test quote.",
                        "Test Author",
                        listOf()
                    ),
                ),
            )
        )
    }

    @Test
    fun `obtainRandomQuote returns null if no quote is found`() = runTest {
        // Setup
        val fakeQuoteRemoteDataSource = FakeQuoteRemoteDataSourceImpl(null)
        val fakeQuoteLocalDataSource = FakeQuoteLocalDataSourceImpl(null)
        val dispatcher = StandardTestDispatcher(testScheduler)

        val quoteRepository = QuoteRepositoryImpl(fakeQuoteRemoteDataSource, fakeQuoteLocalDataSource, dispatcher)

        // Execution
        val results = mutableListOf<Result<Quote>>()
        quoteRepository.obtainRandomQuote().toCollection(results)

        // Assertion
        assertEquals(
            results,
            listOf<Result<Quote>>(
                Result.Error(null)
            )
        )
    }

    @Test
    fun `obtainRandomQuote returns local even if remote is not available`() = runTest {
        // Setup
        val fakeQuoteLocalDto = QuoteLocalDto(
            "id-local",
            "This is a local test quote.",
            "Local Test Author",
            listOf()
        )
        val fakeQuoteRemoteDataSource = FakeQuoteRemoteDataSourceImpl(null)
        val fakeQuoteLocalDataSource = FakeQuoteLocalDataSourceImpl(fakeQuoteLocalDto)
        val dispatcher = StandardTestDispatcher(testScheduler)

        val quoteRepository = QuoteRepositoryImpl(fakeQuoteRemoteDataSource, fakeQuoteLocalDataSource, dispatcher)

        // Execution
        val results = mutableListOf<Result<Quote>>()
        quoteRepository.obtainRandomQuote().toCollection(results)

        // Assertion
        assertEquals(
            results,
            listOf(
                Result.Success(
                    Quote(
                        "id-local",
                        "This is a local test quote.",
                        "Local Test Author",
                        listOf()
                    ),
                ),
                Result.Error(null),
            )
        )
    }

    @Test
    fun `obtainRandomQuote skips local and returns remote when there is no local`() = runTest {
        // Setup
        val fakeQuoteResponseDto = QuoteResponseDto(
            "id",
            "This is a test quote.",
            "Test Author",
            "test-author",
            21,
            listOf()
        )
        val fakeQuoteRemoteDataSource = FakeQuoteRemoteDataSourceImpl(fakeQuoteResponseDto)
        val fakeQuoteLocalDataSource = FakeQuoteLocalDataSourceImpl(null)
        val dispatcher = StandardTestDispatcher(testScheduler)

        val quoteRepository = QuoteRepositoryImpl(fakeQuoteRemoteDataSource, fakeQuoteLocalDataSource, dispatcher)

        // Execution
        val results = mutableListOf<Result<Quote>>()
        quoteRepository.obtainRandomQuote().toCollection(results)

        // Assertion
        assertEquals(
            results,
            listOf(
                Result.Success(
                    Quote(
                        "id",
                        "This is a test quote.",
                        "Test Author",
                        listOf()
                    ),
                ),
            )
        )
    }

}
