package com.attilaszabo.twentyfivedemo.features.cutofftime.presentation

import android.annotation.SuppressLint
import android.graphics.BlurMaskFilter
import android.graphics.Paint
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.attilaszabo.twentyfivedemo.common.domain.CustomResult
import com.attilaszabo.twentyfivedemo.common.domain.getOrNull
import com.attilaszabo.twentyfivedemo.common.presentation.DataState
import com.attilaszabo.twentyfivedemo.common.presentation.theme.DarkGreen
import com.attilaszabo.twentyfivedemo.common.presentation.theme.DarkOrange
import com.attilaszabo.twentyfivedemo.common.presentation.theme.GoldenBrown
import com.attilaszabo.twentyfivedemo.common.presentation.theme.LightGreen
import com.attilaszabo.twentyfivedemo.common.presentation.theme.LightOrange
import com.attilaszabo.twentyfivedemo.common.presentation.theme.Red
import com.attilaszabo.twentyfivedemo.common.presentation.theme.TwentyFiveDemoTheme
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.CutOffTimeRepository
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.GetCutOffTimeStateListUseCase
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.Country
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.CountryIsoCode
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.CutOffTimeDependencies
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.CutOffTimeState
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.models.CutOffTimeStateValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import org.koin.androidx.compose.koinViewModel
import java.time.Duration
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun CutOffTimeScreen(
    onBackClick: () -> Unit,
    viewModel: CutOffTimeViewModel = koinViewModel(),
) {
    val dataState by viewModel.dataState.collectAsStateWithLifecycle()
    CutOffTimeScreen(
        dataState = dataState,
        onBackClick = onBackClick,
        onRetryClick = viewModel::getCutOffTimeStateList,
    )
}

@SuppressLint("UnusedCrossfadeTargetStateParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CutOffTimeScreen(
    onBackClick: () -> Unit,
    onRetryClick: () -> Unit,
    dataState: DataState<RemainingTimeState>,
) {
    val locale = LocalConfiguration.current.locales[0] ?: Locale.getDefault()
    val dateFormatter = remember(locale) {
        DateTimeFormatter.ofPattern("HH:mm, dd MMM yyyy", locale)
    }
    val density = LocalDensity.current

    val animationOverlayTargetValue = remember(dataState is DataState.Success) {
        when {
            dataState is DataState.Success -> 1f
            else -> 0f
        }
    }
    val animatedOverlayWidth by animateFloatAsState(
        targetValue = animationOverlayTargetValue, animationSpec = tween(durationMillis = 1000)
    )
    val animatedOverlayAlpha by animateFloatAsState(
        targetValue = animationOverlayTargetValue, animationSpec = tween(durationMillis = 250)
    )
    val animatedOverlayContentAlpha by animateFloatAsState(
        targetValue = animationOverlayTargetValue, animationSpec = tween(durationMillis = 1000)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                actions = {
                    IconButton(
                        onClick = onBackClick,
                    ) {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = "Close",
                        )
                    }
                },
                title = {}
            )
        },
        content = { paddingValues ->
            Box(
                Modifier
                    .fillMaxSize()
                    .background(GoldenBrown)
            ) {
                if (dataState is DataState.Success) {
                    val currentColor = remember(dataState.data) {
                        with(dataState.data) {
                            lerp(
                                previousState.color,
                                nextState.color,
                                normalizedStepProgress,
                            )
                        }
                    }

                    val animatedColor by animateColorAsState(targetValue = currentColor)

                    val overlayColor = remember(animatedColor) {
                        animatedColor.copy(alpha = 0.15f)
                    }

                    val expireText by remember(dataState.data.isExpired) {
                        mutableStateOf(
                            when {
                                dataState.data.isExpired -> "Expired at"
                                else -> "Expiring at"
                            }
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .alpha(animatedOverlayAlpha)
                            .background(overlayColor)
                            .drawBehind {
                                val offsetFromRightPx = with(density) { 24.dp.toPx() }

                                val topLeft = Offset(0f, 0f)
                                val bottomLeft = Offset(0f, size.height)
                                val rightX = (size.width - offsetFromRightPx) * animatedOverlayWidth
                                val rightY = size.height * dataState.data.normalizedProgress
                                val dynamicPoint = Offset(rightX, rightY)

                                val trianglePath = Path().apply {
                                    moveTo(topLeft.x, topLeft.y)
                                    lineTo(bottomLeft.x, bottomLeft.y)
                                    lineTo(dynamicPoint.x, dynamicPoint.y)
                                    close()
                                }

                                drawPath(
                                    trianglePath, currentColor
                                )

                                drawIntoCanvas { canvas ->
                                    val paint = Paint().apply {
                                        isAntiAlias = true
                                        color = Color.Black.copy(alpha = 0.25f).toArgb()
                                        maskFilter = BlurMaskFilter(36f, BlurMaskFilter.Blur.OUTER)
                                        style = Paint.Style.FILL
                                    }

                                    canvas.nativeCanvas.drawPath(
                                        trianglePath.asAndroidPath(), paint
                                    )
                                }
                            }
                            .padding(start = 24.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        val remainingSeconds = dataState.data.remainingSeconds
                        val hours = remainingSeconds / 3600
                        val minutes = (remainingSeconds % 3600) / 60
                        val seconds = remainingSeconds % 60
                        val timeString = String.format(locale, "%02d:%02d:%02d", hours, minutes, seconds)

                        Column {
                            Text(
                                text = timeString,
                                style = MaterialTheme.typography.displaySmall.copy(
                                    fontWeight = FontWeight.Bold,
                                ),
                                modifier = Modifier.graphicsLayer {
                                    alpha = animatedOverlayContentAlpha
                                }
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "$expireText\n${dateFormatter.format(dataState.data.cutOffDateTime)}",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                ),
                                modifier = Modifier.graphicsLayer {
                                    alpha = animatedOverlayContentAlpha
                                }
                            )
                        }
                    }
                }
                Crossfade(
                    targetState = dataState::class,
                ) { _ ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(PaddingValues(all = 32.dp)),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        when (dataState) {
                            is DataState.Initial, is DataState.Loading -> {
                                CircularProgressIndicator()
                            }

                            is DataState.Success -> {
                            }

                            is DataState.Error -> {
                                Text(
                                    "Error:\n${dataState.message}", textAlign = TextAlign.Center
                                )
                                Spacer(Modifier.height(16.dp))
                                Button(
                                    onClick = onRetryClick
                                ) {
                                    Text("Retry")
                                }
                            }

                            is DataState.Empty -> {
                                Text("An unexpected error occurred")
                            }
                        }
                    }
                }
            }
        }
    )
}

val testIntervalStart: OffsetDateTime
    get() = OffsetDateTime.now().minusHours(2)

val testIntervalEnd: OffsetDateTime
    get() = OffsetDateTime.now().plusHours(12)

@Composable
fun rememberFakeRemainingTimeState(
    fraction: Float = 1f
): DataState<RemainingTimeState> {
    return produceState<DataState<RemainingTimeState>>(
        initialValue = DataState.Loading, key1 = fraction
    ) {
        val cutOffTimeStateList: List<CutOffTimeState> = GetCutOffTimeStateListUseCase(object : CutOffTimeRepository {
            override fun getCutOffTimeWithCountry(): Flow<CustomResult<CutOffTimeDependencies>> = flow {
                emit(
                    CustomResult.success(
                        CutOffTimeDependencies(
                            testIntervalEnd, Country(CountryIsoCode.UK)
                        )
                    )
                )
            }
        }).execute().first().getOrNull()!!

        val offsetDateTime = interpolateTime(fraction)

        value = DataState.Success(
            RemainingTimeState.generate(
                offsetDateTime,
                cutOffTimeStateList,
            )
        )
    }.value
}

private fun interpolateTime(
    fraction: Float,
    start: OffsetDateTime = testIntervalStart,
    end: OffsetDateTime = testIntervalEnd,
): OffsetDateTime {
    val totalDuration = Duration.between(start, end)
    val secondsToAdd = (totalDuration.seconds * fraction.coerceIn(0f, 1f)).toLong()
    return start.plusSeconds(secondsToAdd)
}

@Preview(showBackground = true)
@Composable
fun CutOffTimeScreenSliderPreview() {
    var offsetSliderValue by rememberSaveable { mutableFloatStateOf(1 / 3f) }

    TwentyFiveDemoTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            CutOffTimeScreen(
                onBackClick = {},
                onRetryClick = {},
                dataState = rememberFakeRemainingTimeState(offsetSliderValue),
            )

            Column(
                modifier = Modifier
                    .align(BiasAlignment(0f, 0.75f))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Slider(
                    value = offsetSliderValue, onValueChange = {
                        offsetSliderValue = it
                    }, valueRange = 0f..1f
                )
            }
        }
    }
}

private val CutOffTimeStateValue.color: Color
    get() = when (this) {
        CutOffTimeStateValue.GOOD -> DarkGreen
        CutOffTimeStateValue.NEAR_WARNING -> LightGreen
        CutOffTimeStateValue.WARNING -> LightOrange
        CutOffTimeStateValue.NEAR_EXPIRED -> DarkOrange
        CutOffTimeStateValue.EXPIRED -> Red
    }
