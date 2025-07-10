package com.attilaszabo.twentyfivedemo.quote.data

import com.attilaszabo.twentyfivedemo.quoteapi.QuoteResponseDto
import com.attilaszabo.twentyfivedemo.quotecache.QuoteLocalDto
import com.attilaszabo.twentyfivedemo.quote.domain.models.Quote
import org.junit.Assert.assertEquals
import org.junit.Test

class QuoteMapperTest {
    @Test
    fun `QuoteLocalDto's mapToDomain maps correctly`() {
        // Setup
        val fakeQuoteLocalDto = QuoteLocalDto(
            "id",
            "This is a test quote.",
            "Test Author",
            listOf("tag")
        )

        // Execution
        val result = fakeQuoteLocalDto.mapToDomain()

        // Assertion
        assertEquals(
            result,
            Quote(
                "id",
                "This is a test quote.",
                "Test Author",
                listOf("tag")
            )
        )
    }

    @Test
    fun `QuoteResponseDto's mapToLocalDto maps correctly`() {
        // Setup
        val fakeQuoteResponseDto = QuoteResponseDto(
            "id",
            "This is a test quote.",
            "Test Author",
            "test-author",
            21,
            listOf("tag")
        )

        // Execution
        val result = fakeQuoteResponseDto.mapToLocalDto()

        // Assertion
        assertEquals(
            result,
            QuoteLocalDto(
                "id",
                "This is a test quote.",
                "Test Author",
                listOf("tag")
            )
        )
    }

    @Test
    fun `QuoteResponseDto's mapToDomain maps correctly`() {
        // Setup
        val fakeQuoteResponseDto = QuoteResponseDto(
            "id",
            "This is a test quote.",
            "Test Author",
            "test-author",
            21,
            listOf("tag")
        )

        // Execution
        val result = fakeQuoteResponseDto.mapToDomain()

        // Assertion
        assertEquals(
            result,
            Quote(
                "id",
                "This is a test quote.",
                "Test Author",
                listOf("tag")
            )
        )
    }

    @Test
    fun `Quote's mapToResponseDto maps correctly`() {
        // Setup
        val fakeQuote = Quote(
            "id",
            "This is a test quote.",
            "Test Author",
            listOf()
        )

        // Execution
        val result = fakeQuote.mapToResponseDto()

        // Assertion
        assertEquals(
            result,
            QuoteResponseDto(
                "id",
                "This is a test quote.",
                "Test Author",
                "",
                21,
                listOf()
            )
        )
    }
}
