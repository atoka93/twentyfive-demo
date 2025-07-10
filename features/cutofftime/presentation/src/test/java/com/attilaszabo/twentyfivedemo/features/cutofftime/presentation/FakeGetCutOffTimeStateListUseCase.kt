package com.attilaszabo.twentyfivedemo.features.cutofftime.presentation

import com.attilaszabo.twentyfivedemo.common.domain.CustomResult
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.CutOffTimeRepository
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.GetCutOffTimeStateListUseCase
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.CutOffTimeDependencies
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.CutOffTimeState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow

class FakeGetCutOffTimeStateListUseCase :
    GetCutOffTimeStateListUseCase(
        cutOffTimeRepository = object : CutOffTimeRepository {
            override fun getCutOffTimeWithCountry(): Flow<CustomResult<CutOffTimeDependencies>> = flow {}
        }
    ) {
    val flow = MutableSharedFlow<CustomResult<List<CutOffTimeState>>>(
        replay = Int.MAX_VALUE
    )

    override fun execute(): Flow<CustomResult<List<CutOffTimeState>>> = flow
}
