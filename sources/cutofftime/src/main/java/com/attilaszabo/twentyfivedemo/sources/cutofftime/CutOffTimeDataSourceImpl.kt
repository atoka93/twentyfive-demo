package com.attilaszabo.twentyfivedemo.sources.cutofftime

import kotlinx.coroutines.delay
import java.time.OffsetDateTime

class CutOffTimeDataSourceImpl : CutOffTimeDataSource {
    override suspend fun getLockingInfo(): CutOffTime {
        delay(500)
        return CutOffTime(
            OffsetDateTime.now().withHour(23).withMinute(59).withSecond(59)
        )
    }
}
