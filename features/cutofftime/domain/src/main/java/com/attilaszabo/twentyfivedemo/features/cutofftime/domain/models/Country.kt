package com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models

import java.time.Duration

data class Country(
    val countryCode: CountryIsoCode
) {
    internal fun getTransitionStartOffset(cutOffTimeStateValue: CutOffTimeStateValue): Duration {
        return when (cutOffTimeStateValue) {
            CutOffTimeStateValue.GOOD -> Duration.ofHours(12)
            CutOffTimeStateValue.NEAR_WARNING -> getTransitionStartOffset(CutOffTimeStateValue.WARNING)
                .plus(Duration.ofMinutes(5))

            // Data hardcoded here could also be / come from a data source
            CutOffTimeStateValue.WARNING -> when (countryCode) {
                CountryIsoCode.UK -> Duration.ofHours(3)
                CountryIsoCode.DE -> Duration.ofHours(2)
            }

            CutOffTimeStateValue.NEAR_EXPIRED -> getTransitionStartOffset(CutOffTimeStateValue.EXPIRED)
                .plus(Duration.ofMinutes(15))

            CutOffTimeStateValue.EXPIRED -> Duration.ZERO
        }
    }
}
