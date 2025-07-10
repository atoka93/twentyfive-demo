package com.attilaszabo.twentyfivedemo.features.cutofftime.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attilaszabo.twentyfivedemo.common.domain.CustomResult
import com.attilaszabo.twentyfivedemo.common.presentation.DataState
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.GetCutOffTimeStateListUseCase
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.CutOffTimeState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CutOffTimeViewModel(
    private val getCutOffTimeStateListUseCase: GetCutOffTimeStateListUseCase,
    private val clock: CustomClock,
    private val backgroundDispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _dataState = MutableStateFlow<DataState<RemainingTimeState>>(DataState.Initial)
    val dataState: StateFlow<DataState<RemainingTimeState>> = _dataState

    private var _fetchJob: Job? = null
    private var _timerJob: Job? = null

    init {
        getCutOffTimeStateList()
    }

    fun getCutOffTimeStateList() {
        _fetchJob?.cancel()
        _fetchJob = viewModelScope.launch {
            getCutOffTimeStateListUseCase
                .execute()
                .onStart {
                    _dataState.value = DataState.Loading
                }
                .catch { cause ->
                    emit(CustomResult.failure(cause))
                }
                .collect { quoteResult ->
                    when (quoteResult) {
                        is CustomResult.success -> setTimer(quoteResult.data)
                        is CustomResult.failure -> _dataState.value = DataState.Error(quoteResult.exception.toString())
                    }
                }
        }
    }

    private fun setTimer(cutOffTimeStateList: List<CutOffTimeState>) {
        _timerJob?.cancel()
        _timerJob = viewModelScope.launch(backgroundDispatcher) {
            do {
                val remainingTimeState = RemainingTimeState.generate(
                    clock.now(),
                    cutOffTimeStateList,
                )
                _dataState.value = DataState.Success(
                    remainingTimeState
                )
                delay(1000L)
            } while (remainingTimeState.remainingSeconds > 0)
        }
    }
}
