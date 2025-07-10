package com.attilaszabo.twentyfivedemo.sources.country

interface CountryDataSource {
    suspend fun getCountry(): Country
}
