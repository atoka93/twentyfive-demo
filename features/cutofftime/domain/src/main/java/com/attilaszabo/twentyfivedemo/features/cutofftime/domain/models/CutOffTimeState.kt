package com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models

import java.time.OffsetDateTime

data class CutOffTimeState(
    val value: CutOffTimeStateValue,
    val offsetDateTime: OffsetDateTime,
)
