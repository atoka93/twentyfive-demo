package com.attilaszabo.twentyfivedemo.features.cutofftime.domain

import com.attilaszabo.twentyfivedemo.common.domain.CustomResult
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.Country
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.CountryIsoCode
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.CutOffTimeDependencies
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.CutOffTimeState
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.CutOffTimeStateValue
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Duration
import java.time.OffsetDateTime

class GetCutOffTimeStateListUseCaseTest {
    private fun getCutOffTimeState(inputDateTime: OffsetDateTime, warningOffsetHours: Long) = listOf(
        CutOffTimeState(
            CutOffTimeStateValue.GOOD,
            inputDateTime.minus(Duration.ofHours(12))
        ),
        CutOffTimeState(
            CutOffTimeStateValue.NEAR_WARNING,
            inputDateTime.minus(Duration.ofHours(warningOffsetHours).plus(Duration.ofMinutes(5)))
        ),
        CutOffTimeState(
            CutOffTimeStateValue.WARNING,
            inputDateTime.minus(Duration.ofHours(warningOffsetHours))
        ),
        CutOffTimeState(
            CutOffTimeStateValue.NEAR_EXPIRED,
            inputDateTime.minus(Duration.ofMinutes(15))
        ),
        CutOffTimeState(
            CutOffTimeStateValue.EXPIRED,
            inputDateTime
        ),
    )

    @Test
    fun `execute returns result success with the correct cut-off time state for United Kingdom`() = runTest {
        val inputDateTime = OffsetDateTime.now()
        val cutOffTimeDependencies = CutOffTimeDependencies(
            cutOffTime = inputDateTime,
            country = Country(
                countryCode = CountryIsoCode.UK,
            ),
        )
        val fakeCutOffTimeRepository = FakeCutOffTimeRepository(CustomResult.success(cutOffTimeDependencies))
        val getCutOffTimeStateListUseCase = GetCutOffTimeStateListUseCase(fakeCutOffTimeRepository)

        val result = getCutOffTimeStateListUseCase.execute()

        val cutOffTimeState = getCutOffTimeState(inputDateTime, 3)
        result.collect {
            assertEquals(CustomResult.success(cutOffTimeState), it)
        }
    }

    @Test
    fun `execute returns result success with the correct cut-off time state for Germany`() = runTest {
        val inputDateTime = OffsetDateTime.now()
        val cutOffTimeDependencies = CutOffTimeDependencies(
            cutOffTime = inputDateTime,
            country = Country(
                countryCode = CountryIsoCode.DE,
            ),
        )
        val fakeCutOffTimeRepository = FakeCutOffTimeRepository(CustomResult.success(cutOffTimeDependencies))
        val getCutOffTimeStateListUseCase = GetCutOffTimeStateListUseCase(fakeCutOffTimeRepository)

        val result = getCutOffTimeStateListUseCase.execute()

        val cutOffTimeState = getCutOffTimeState(inputDateTime, 2)
        result.collect {
            assertEquals(CustomResult.success(cutOffTimeState), it)
        }
    }

    @Test
    fun `execute returns result failure if the repository fails`() = runBlocking {
        val testException = Exception("Test exception")
        val fakeCutOffTimeRepository = FakeCutOffTimeRepository(CustomResult.failure(testException))
        val getCutOffTimeStateListUseCase = GetCutOffTimeStateListUseCase(fakeCutOffTimeRepository)

        val result = getCutOffTimeStateListUseCase.execute()

        result.collect {
            assertEquals(CustomResult.failure<CutOffTimeState>(testException), it)
        }
    }
}
