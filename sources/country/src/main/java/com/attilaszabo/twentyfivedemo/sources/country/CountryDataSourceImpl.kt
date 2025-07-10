package com.attilaszabo.twentyfivedemo.sources.country

class CountryDataSourceImpl : CountryDataSource {
    override suspend fun getCountry(): Country {
        return Country.UK
    }
}
