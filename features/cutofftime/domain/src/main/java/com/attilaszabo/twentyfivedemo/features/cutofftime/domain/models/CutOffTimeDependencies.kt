package com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models

import java.time.OffsetDateTime

data class CutOffTimeDependencies(
    val cutOffTime: OffsetDateTime,
    val country: Country,
)
