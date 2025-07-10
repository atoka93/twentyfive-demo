package com.attilaszabo.twentyfivedemo.features.cutofftime.domain

import com.attilaszabo.twentyfivedemo.common.domain.CustomResult
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.CutOffTimeDependencies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCutOffTimeRepository(
    private val result: CustomResult<CutOffTimeDependencies>,
) : CutOffTimeRepository {
    override fun getCutOffTimeWithCountry(): Flow<CustomResult<CutOffTimeDependencies>> =
        flow {
            emit(result)
        }
}
