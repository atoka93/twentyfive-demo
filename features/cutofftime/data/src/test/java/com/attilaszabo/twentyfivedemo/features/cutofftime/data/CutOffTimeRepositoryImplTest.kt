package com.attilaszabo.twentyfivedemo.features.cutofftime.data

import com.attilaszabo.twentyfivedemo.common.domain.exceptionOrNull
import com.attilaszabo.twentyfivedemo.common.domain.getOrNull
import com.attilaszabo.twentyfivedemo.common.domain.isFailure
import com.attilaszabo.twentyfivedemo.common.domain.isSuccess
import com.attilaszabo.twentyfivedemo.features.cutofftime.data.dependencies.FakeCountryDataSource
import com.attilaszabo.twentyfivedemo.features.cutofftime.data.dependencies.FakeCutOffTimeDataSource
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.Country
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.CutOffTimeDependencies
import com.attilaszabo.twentyfivedemo.sources.country.CountryDataSource
import com.attilaszabo.twentyfivedemo.sources.cutofftime.CutOffTime
import com.attilaszabo.twentyfivedemo.sources.cutofftime.CutOffTimeDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.OffsetDateTime
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.CountryIsoCode as OutputCountryIsoCode
import com.attilaszabo.twentyfivedemo.sources.country.Country as InputCountryIsoCode

@OptIn(ExperimentalCoroutinesApi::class)
class CutOffTimeRepositoryImplTest {
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getCutOffTimeWithCountry emits result successfully`() = runTest {
        val inputTime = OffsetDateTime.now()
        val cutOffTimeDataSource: CutOffTimeDataSource =
            FakeCutOffTimeDataSource {
                CutOffTime(inputTime)
            }
        val countryDataSource: CountryDataSource =
            FakeCountryDataSource {
                InputCountryIsoCode.UK
            }
        val repository = CutOffTimeRepositoryImpl(
            cutOffTimeDataSource,
            countryDataSource,
            testDispatcher
        )
        val dispatcher = StandardTestDispatcher(testScheduler)
        val scope = TestScope(dispatcher)

        repository.getCutOffTimeWithCountry().collect {
            assertTrue(dispatcher.isDispatchNeeded(scope.coroutineContext))
            val expected = CutOffTimeDependencies(
                inputTime,
                Country(
                    countryCode = OutputCountryIsoCode.UK,
                ),
            )
            assertTrue(it.isSuccess)
            assertEquals(expected, it.getOrNull())
        }
    }

    @Test
    fun `getCutOffTimeWithCountry emits failure when exception is thrown during time retrieval`() = runTest {
        val testException = Exception("test exception")
        val inputTime = OffsetDateTime.now()
        val cutOffTimeDataSource: CutOffTimeDataSource =
            FakeCutOffTimeDataSource {
                CutOffTime(inputTime)
            }
        val countryDataSource: CountryDataSource =
            FakeCountryDataSource {
                throw testException
            }
        val repository = CutOffTimeRepositoryImpl(
            cutOffTimeDataSource,
            countryDataSource,
            testDispatcher
        )
        val dispatcher = StandardTestDispatcher(testScheduler)
        val scope = TestScope(dispatcher)

        repository.getCutOffTimeWithCountry().collect {
            assertTrue(dispatcher.isDispatchNeeded(scope.coroutineContext))
            assertTrue(it.isFailure)
            assertEquals(testException, it.exceptionOrNull())
        }
    }

    @Test
    fun `getCutOffTimeWithCountry emits failure when exception is thrown during country retrieval`() = runTest {
        val testException = Exception("test exception")
        val cutOffTimeDataSource: CutOffTimeDataSource =
            FakeCutOffTimeDataSource {
                throw testException
            }
        val countryDataSource: CountryDataSource =
            FakeCountryDataSource {
                InputCountryIsoCode.UK
            }
        val repository = CutOffTimeRepositoryImpl(
            cutOffTimeDataSource,
            countryDataSource,
            testDispatcher
        )
        val dispatcher = StandardTestDispatcher(testScheduler)
        val scope = TestScope(dispatcher)

        repository.getCutOffTimeWithCountry().collect {
            assertTrue(dispatcher.isDispatchNeeded(scope.coroutineContext))
            assertTrue(it.isFailure)
            assertEquals(testException, it.exceptionOrNull())
        }
    }
}
