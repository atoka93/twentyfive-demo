package com.attilaszabo.twentyfivedemo.features.cutofftime.data.dependencies

import com.attilaszabo.twentyfivedemo.sources.country.Country
import com.attilaszabo.twentyfivedemo.sources.country.CountryDataSource

class FakeCountryDataSource(
    private val getOutputCountry: () -> Country,
) : CountryDataSource {
    override suspend fun getCountry(): Country {
        return getOutputCountry()
    }
}
