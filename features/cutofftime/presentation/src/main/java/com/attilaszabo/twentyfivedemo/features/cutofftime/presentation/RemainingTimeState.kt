package com.attilaszabo.twentyfivedemo.features.cutofftime.presentation

import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.CutOffTimeState
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.CutOffTimeStateValue
import java.time.Duration
import java.time.OffsetDateTime

data class RemainingTimeState(
    val currentDateTime: OffsetDateTime,
    val cutOffDateTime: OffsetDateTime,
    val remainingSeconds: Long,
    val normalizedProgress: Float,
    val normalizedStepProgress: Float,
    val previousState: CutOffTimeStateValue,
    val nextState: CutOffTimeStateValue,
) {
    val isExpired: Boolean
        get() = remainingSeconds <= 0

    companion object {
        fun generate(
            currentDateTime: OffsetDateTime,
            cutOffTimeStateList: List<CutOffTimeState>,
        ): RemainingTimeState {
            val cutOffDateTime = cutOffTimeStateList.last().offsetDateTime
            val animationSpan = Duration.between(cutOffTimeStateList.first().offsetDateTime, cutOffDateTime).seconds.coerceAtLeast(1)
            val remainingDuration = Duration.between(currentDateTime, cutOffDateTime)
            val remainingSeconds = remainingDuration.seconds.coerceAtLeast(0)
            val normalizedProgress = (remainingSeconds.toFloat() / animationSpan).coerceIn(0f, 1f)

            val currentStateIndex = cutOffTimeStateList.indexOfFirst { it.offsetDateTime.isAfter(currentDateTime) }
            val normalizedStepProgress: Float
            val previousState: CutOffTimeStateValue
            val nextState: CutOffTimeStateValue
            when (currentStateIndex) {
                0 -> {
                    normalizedStepProgress = 0f
                    previousState = cutOffTimeStateList.first().value
                    nextState = cutOffTimeStateList.first().value
                }

                -1 -> {
                    normalizedStepProgress = 1f
                    previousState = cutOffTimeStateList.last().value
                    nextState = cutOffTimeStateList.last().value
                }

                else -> {
                    normalizedStepProgress = currentDateTime.normalizeOffsetDateTime(
                        cutOffTimeStateList[currentStateIndex - 1].offsetDateTime,
                        cutOffTimeStateList[currentStateIndex].offsetDateTime,
                    )
                    previousState = cutOffTimeStateList[currentStateIndex - 1].value
                    nextState = cutOffTimeStateList[currentStateIndex].value
                }
            }
            return RemainingTimeState(
                currentDateTime = currentDateTime,
                cutOffDateTime = cutOffDateTime,
                remainingSeconds = remainingSeconds,
                normalizedProgress = normalizedProgress,
                normalizedStepProgress = normalizedStepProgress,
                previousState = previousState,
                nextState = nextState,
            )
        }
    }
}

private fun OffsetDateTime.normalizeOffsetDateTime(
    start: OffsetDateTime,
    end: OffsetDateTime,
): Float {
    val totalDuration = Duration.between(start, end).toMillis().coerceAtLeast(1)
    val elapsedDuration = Duration.between(start, this).toMillis().coerceIn(0, totalDuration)
    return elapsedDuration.toFloat() / totalDuration
}
