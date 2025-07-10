package com.attilaszabo.twentyfivedemo.quotecache

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class QuoteLocalDataSourceImplTest {
    @Test
    fun `quoteFlow returns quote`() = runTest {
        // Setup
        val fakeQuoteProto = QuoteProto.newBuilder()
            .setId("id")
            .setContent("This is a test quote.")
            .setAuthor("Test Author")
            .addAllTags(listOf("tag"))
            .build()
        val fakeDataStore = FakeQuoteDataStore(fakeQuoteProto)

        val dataSource = QuoteLocalDataSourceImpl(fakeDataStore)

        // Execution
        val result = dataSource.quoteFlow

        // Assertion
        result.collect { quote ->
            assertEquals(
                quote,
                QuoteLocalDto(
                    "id",
                    "This is a test quote.",
                    "Test Author",
                    listOf("tag"),
                )
            )
        }
    }

    @Test
    fun `saveQuote saves the correct data`() = runTest {
        // Setup
        val fakeDataStore = FakeQuoteDataStore(null)

        val dataSource = QuoteLocalDataSourceImpl(fakeDataStore)

        // Execution
        dataSource.saveQuote(
            QuoteLocalDto(
                "id",
                "This is a test quote.",
                "Test Author",
                listOf("tag"),
            )
        )

        // Assertion
        assertEquals(
            fakeDataStore.inputQuote,
            QuoteProto.newBuilder()
                .setId("id")
                .setContent("This is a test quote.")
                .setAuthor("Test Author")
                .addAllTags(listOf("tag"))
                .build()
        )
    }
}
