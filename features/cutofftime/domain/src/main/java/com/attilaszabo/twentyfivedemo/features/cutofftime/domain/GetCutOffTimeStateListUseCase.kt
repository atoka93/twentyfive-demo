package com.attilaszabo.twentyfivedemo.features.cutofftime.domain

import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.CutOffTimeDependencies
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.CutOffTimeState
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.CutOffTimeStateValue
import com.attilaszabo.twentyfivedemo.common.domain.CustomResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

open class GetCutOffTimeStateListUseCase(
    private val cutOffTimeRepository: CutOffTimeRepository,
) {
    open fun execute(): Flow<CustomResult<List<CutOffTimeState>>> {
        return cutOffTimeRepository
            .getCutOffTimeWithCountry()
            .map { value ->
                when (value) {
                    is CustomResult.failure<CutOffTimeDependencies> ->
                        return@map CustomResult.failure<List<CutOffTimeState>>(value.exception)

                    is CustomResult.success<CutOffTimeDependencies> -> {
                        val cutOffTimeDependencies = value.data
                        val cutOffTime = cutOffTimeDependencies.cutOffTime
                        val country = cutOffTimeDependencies.country

                        val cutOffTimeStateList: List<CutOffTimeState> = enumValues<CutOffTimeStateValue>().map {
                            CutOffTimeState(
                                value = it,
                                offsetDateTime = cutOffTime.minus(country.getTransitionStartOffset(it)),
                            )
                        }

                        return@map CustomResult.success(cutOffTimeStateList)
                    }
                }
            }
    }
}
