package com.attilaszabo.twentyfivedemo.features.cutofftime.data

import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.Country
import org.junit.Assert.assertEquals
import org.junit.Test
import com.attilaszabo.twentyfivedemo.sources.country.Country as InputCountryIsoCode
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.CountryIsoCode as OutputCountryIsoCode

class CountryMapperTest {
    @Test
    fun `should convert UK input to UK domain country`() {
        val input = InputCountryIsoCode.UK

        val output = input.toDomainCountry()

        val expected = Country(OutputCountryIsoCode.UK)

        assertEquals(expected, output)
    }

    @Test
    fun `should convert DE input to DE domain country`() {
        val input = InputCountryIsoCode.DE

        val output = input.toDomainCountry()

        val expected = Country(OutputCountryIsoCode.DE)

        assertEquals(expected, output)
    }
}
