package com.attilaszabo.twentyfivedemo.features.cutofftime.data.dependencies

import com.attilaszabo.twentyfivedemo.sources.cutofftime.CutOffTime
import com.attilaszabo.twentyfivedemo.sources.cutofftime.CutOffTimeDataSource

class FakeCutOffTimeDataSource(
    private val getOutputCutOffTime: () -> CutOffTime,
) : CutOffTimeDataSource {
    override suspend fun getLockingInfo(): CutOffTime {
        return getOutputCutOffTime()
    }
}
