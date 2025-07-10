package com.attilaszabo.twentyfivedemo.features.cutofftime.data

import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.Country
import com.attilaszabo.twentyfivedemo.sources.country.Country as InputCountryIsoCode
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.CountryIsoCode as OutputCountryIsoCode

fun InputCountryIsoCode.toDomainCountry(): Country {
    return Country(
        when (this) {
            InputCountryIsoCode.UK -> OutputCountryIsoCode.UK
            InputCountryIsoCode.DE -> OutputCountryIsoCode.DE
        }
    )
}
