package com.attilaszabo.twentyfivedemo.sources.cutofftime

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class CutOffTimeDataSourceImplTest {

    private val dataSource = CutOffTimeDataSourceImpl()

    @Test
    fun `getLockingInfo returns CutOffTime with correct time`() = runTest {
        val cutOffTime = dataSource.getLockingInfo()

        val resultTime = cutOffTime.lockTime

        assertEquals(23, resultTime.hour)
        assertEquals(59, resultTime.minute)
        assertEquals(59, resultTime.second)
    }
}
