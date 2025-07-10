package com.attilaszabo.twentyfivedemo.sources.cutofftime

interface CutOffTimeDataSource {
    suspend fun getLockingInfo(): CutOffTime
}
