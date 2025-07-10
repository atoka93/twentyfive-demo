package com.attilaszabo.twentyfivedemo.quote.presentation

import com.attilaszabo.twentyfivedemo.common.domain.CustomResult
import com.attilaszabo.twentyfivedemo.common.presentation.DataState
import com.attilaszabo.twentyfivedemo.quote.domain.GetRandomQuoteUseCase
import com.attilaszabo.twentyfivedemo.quote.domain.models.Quote
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class QuoteViewModelTest {
    private lateinit var fakeQuoteRepository: FakeQuoteRepositoryImpl
    private lateinit var viewModel: QuoteViewModel

    @Before
    fun setup() {
        fakeQuoteRepository = FakeQuoteRepositoryImpl()
        viewModel = QuoteViewModel(
            getRandomQuoteUseCase = GetRandomQuoteUseCase(fakeQuoteRepository)
        )
    }

    @Test
    fun dataState_whenInitialized_thenShowsLoading() = runTest {
        assertEquals(
            DataState.Loading,
            viewModel.dataState.value
        )
    }

    @Test
    fun dataState_whenQuoteIsEmitted_thenShowsSuccess() = runTest {
        val fakeQuote = Quote(
            "id",
            "This is a test quote.",
            "Test Author",
            listOf()
        )
        fakeQuoteRepository.flow.emit(CustomResult.success(fakeQuote))

        assertEquals(
            DataState.Success(fakeQuote),
            viewModel.dataState.first()
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun dataState_whenErrorIsEmitted_thenShowsError() = runTest {
        val exception = Exception("Test exception")
        fakeQuoteRepository.flow.emit(CustomResult.failure(exception))

        advanceUntilIdle()

        assertEquals(
            DataState.Error(exception.toString()),
            viewModel.dataState.value
        )
    }
}
