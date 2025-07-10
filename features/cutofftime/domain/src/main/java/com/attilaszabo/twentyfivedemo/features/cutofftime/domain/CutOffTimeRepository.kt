package com.attilaszabo.twentyfivedemo.features.cutofftime.domain

import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.CutOffTimeDependencies
import com.attilaszabo.twentyfivedemo.common.domain.CustomResult
import kotlinx.coroutines.flow.Flow

interface CutOffTimeRepository {
    fun getCutOffTimeWithCountry(): Flow<CustomResult<CutOffTimeDependencies>>
}
