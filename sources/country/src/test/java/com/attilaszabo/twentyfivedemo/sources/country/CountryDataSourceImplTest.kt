package com.attilaszabo.twentyfivedemo.sources.country

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class CountryDataSourceImplTest {

    private val dataSource = CountryDataSourceImpl()

    @Test
    fun `getCountry returns UK`() = runTest {
        val countryCode = dataSource.getCountry()

        assertEquals(Country.UK, countryCode)
    }
}
