package com.attilaszabo.twentyfivedemo.features.cutofftime.presentation

import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.CutOffTimeState
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.CutOffTimeStateValue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.Duration
import java.time.OffsetDateTime

class RemainingTimeStateTest {
    private fun generateInput(): Pair<OffsetDateTime, List<CutOffTimeState>> {
        val dateTime = OffsetDateTime.now()
        val cutOffTimeStateList = enumValues<CutOffTimeStateValue>().map {
            val duration = when (it) {
                CutOffTimeStateValue.GOOD -> Duration.ofHours(12)
                CutOffTimeStateValue.NEAR_WARNING -> Duration.ofHours(4).plus(Duration.ofMinutes(5))
                CutOffTimeStateValue.WARNING -> Duration.ofHours(4)
                CutOffTimeStateValue.NEAR_EXPIRED -> Duration.ofMinutes(30)
                CutOffTimeStateValue.EXPIRED -> Duration.ZERO
            }
            CutOffTimeState(
                value = it,
                offsetDateTime = dateTime.minus(duration),
            )
        }
        return dateTime to cutOffTimeStateList
    }

    @Test
    fun `generate output is correct if date time is before set states`() {
        val (dateTime, cutOffTimeStateList) = generateInput()

        val currentDateTime = dateTime.minusDays(10)

        val result = RemainingTimeState.generate(currentDateTime, cutOffTimeStateList)

        val remainingTimeState = RemainingTimeState(
            currentDateTime = currentDateTime,
            cutOffDateTime = cutOffTimeStateList.last().offsetDateTime,
            remainingSeconds = Duration.between(currentDateTime, cutOffTimeStateList.last().offsetDateTime).seconds,
            normalizedProgress = 1f,
            normalizedStepProgress = 0f,
            previousState = CutOffTimeStateValue.GOOD,
            nextState = CutOffTimeStateValue.GOOD,
        )
        assertEquals(remainingTimeState, result)
        assertFalse(result.isExpired)
    }

    @Test
    fun `generate output is correct if date time is after set states`() {
        val (dateTime, cutOffTimeStateList) = generateInput()

        val currentDateTime = dateTime.plusDays(10)

        val result = RemainingTimeState.generate(currentDateTime, cutOffTimeStateList)

        val remainingTimeState = RemainingTimeState(
            currentDateTime = currentDateTime,
            cutOffDateTime = cutOffTimeStateList.last().offsetDateTime,
            remainingSeconds = 0,
            normalizedProgress = 0f,
            normalizedStepProgress = 1f,
            previousState = CutOffTimeStateValue.EXPIRED,
            nextState = CutOffTimeStateValue.EXPIRED,
        )
        assertEquals(remainingTimeState, result)
        assertTrue(result.isExpired)
    }

    @Test
    fun `generate output is correct if date time is in between states`() {
        val (_, cutOffTimeStateList) = generateInput()

        (0..cutOffTimeStateList.size - 2).forEach { index ->
            val currentDateTime = cutOffTimeStateList[index].offsetDateTime.plusSeconds(
                Duration.between(
                    cutOffTimeStateList[index].offsetDateTime,
                    cutOffTimeStateList[index + 1].offsetDateTime
                ).seconds / 2
            )

            val result = RemainingTimeState.generate(currentDateTime, cutOffTimeStateList)

            val remainingSeconds = Duration.between(currentDateTime, cutOffTimeStateList.last().offsetDateTime).seconds
            val animationSpan = Duration.between(
                cutOffTimeStateList.first().offsetDateTime,
                cutOffTimeStateList.last().offsetDateTime
            ).seconds

            val remainingTimeState = RemainingTimeState(
                currentDateTime = currentDateTime,
                cutOffDateTime = cutOffTimeStateList.last().offsetDateTime,
                remainingSeconds = remainingSeconds,
                normalizedProgress = (remainingSeconds.toFloat() / animationSpan).coerceIn(0f, 1f),
                normalizedStepProgress = 0.5f,
                previousState = cutOffTimeStateList[index].value,
                nextState = cutOffTimeStateList[index + 1].value,
            )
            assertEquals(remainingTimeState, result)
            assertFalse(result.isExpired)
        }
    }
}
