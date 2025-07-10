package com.attilaszabo.twentyfivedemo.features.cutofftime.presentation

import java.time.OffsetDateTime

fun interface CustomClock {
    fun now(): OffsetDateTime
}
