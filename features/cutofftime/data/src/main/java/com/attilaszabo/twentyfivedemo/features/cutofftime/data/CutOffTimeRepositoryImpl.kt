package com.attilaszabo.twentyfivedemo.features.cutofftime.data

import com.attilaszabo.twentyfivedemo.common.domain.CustomResult
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.CutOffTimeRepository
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.CutOffTimeDependencies
import com.attilaszabo.twentyfivedemo.sources.country.CountryDataSource
import com.attilaszabo.twentyfivedemo.sources.cutofftime.CutOffTimeDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CutOffTimeRepositoryImpl(
    private val cutOffTimeDataSource: CutOffTimeDataSource,
    private val countryDataSource: CountryDataSource,
    private val dispatcher: CoroutineDispatcher,
) : CutOffTimeRepository {

    override fun getCutOffTimeWithCountry(): Flow<CustomResult<CutOffTimeDependencies>> =
        flow {
            val cutOffTime = cutOffTimeDataSource.getLockingInfo().lockTime
            val countryCode = countryDataSource.getCountry()

            emit(
                CustomResult.success(
                    CutOffTimeDependencies(
                        cutOffTime,
                        countryCode.toDomainCountry(),
                    )
                )
            )
        }
            .catch<CustomResult<CutOffTimeDependencies>> { cause ->
                emit(CustomResult.failure(cause))
            }
            .flowOn(dispatcher)
}
