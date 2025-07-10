package com.attilaszabo.twentyfivedemo.features.cutofftime.presentation

import com.attilaszabo.twentyfivedemo.common.domain.CustomResult
import com.attilaszabo.twentyfivedemo.common.presentation.DataState
import com.attilaszabo.twentyfivedemo.common.sharedtestresources.MainCoroutineRule
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.CutOffTimeState
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.CutOffTimeStateValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.OffsetDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class CutOffTimeViewModelTest {
    private lateinit var fakeGetCutOffTimeStateListUseCase: FakeGetCutOffTimeStateListUseCase
    private lateinit var viewModel: CutOffTimeViewModel
    private var dateTime: OffsetDateTime = OffsetDateTime.now()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        fakeGetCutOffTimeStateListUseCase = FakeGetCutOffTimeStateListUseCase()
        viewModel = CutOffTimeViewModel(
            getCutOffTimeStateListUseCase = fakeGetCutOffTimeStateListUseCase,
            clock = { dateTime },
            backgroundDispatcher = mainCoroutineRule.dispatcher,
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
    fun dataState_whenCutOffTimeStateIsEmitted_thenShowsSuccess() = runTest {
        val fakeCutOffTimeStateList = listOf(
            CutOffTimeState(
                CutOffTimeStateValue.GOOD,
                dateTime,
            ),
            CutOffTimeState(
                CutOffTimeStateValue.EXPIRED,
                dateTime,
            )
        )
        fakeGetCutOffTimeStateListUseCase.flow.emit(CustomResult.success(fakeCutOffTimeStateList))

        advanceUntilIdle()

        val remainingTimeState = RemainingTimeState(
            currentDateTime = dateTime,
            cutOffDateTime = dateTime,
            remainingSeconds = 0,
            normalizedProgress = 0f,
            normalizedStepProgress = 1f,
            previousState = CutOffTimeStateValue.EXPIRED,
            nextState = CutOffTimeStateValue.EXPIRED,
        )
        assertEquals(
            DataState.Success(remainingTimeState),
            viewModel.dataState.first()
        )
    }

    @Test
    fun dataState_whenErrorIsEmitted_thenShowsError() = runTest {
        val exception = Exception("Test exception")
        fakeGetCutOffTimeStateListUseCase.flow.emit(CustomResult.failure(exception))

        advanceUntilIdle()

        assertEquals(
            DataState.Error(exception.toString()),
            viewModel.dataState.first()
        )
    }
}
