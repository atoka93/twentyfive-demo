package com.attilaszabo.twentyfivedemo.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.attilaszabo.twentyfivedeom.commonpresentation.theme.TwentyFiveDemoTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cos

@Composable
fun MainScreen(
    onQuotesClick: () -> Unit
) {
    val rotationDegrees = 60f
    val animationDuration = 1500
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenWidthPx = with(density) { screenWidth.toPx() }
    var playForward by remember { mutableStateOf(true) }

    val rotation by animateFloatAsState(
        targetValue = if (playForward) {
            rotationDegrees
        } else {
            0f
        },
        animationSpec = tween(
            durationMillis = animationDuration
        )
    )

    val translationX by animateFloatAsState(
        targetValue = if (playForward) {
            val rotatedCornerOffset = screenWidthPx * cos(Math.toRadians(rotationDegrees.toDouble())).toFloat()
            16.0f - rotatedCornerOffset
        } else {
            0f
        },
        animationSpec = tween(
            durationMillis = animationDuration
        )
    )

    LaunchedEffect(Unit) {
        playForward = true
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer(
                    translationX = translationX,
                    rotationZ = rotation,
                    shadowElevation = 16.0f,
                    shape = RectangleShape,
                    clip = false
                )
                .size(screenWidth)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Magenta,
                            Color.Cyan
                        )
                    ),
                    shape = RectangleShape
                )
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(WindowInsets.systemBars.asPaddingValues()),
        ) {
            Button(
                onClick = {
                    playForward = !playForward
                }
            ) {
//                if (playForward) "Reverse" else "Play"
                Text("Animate background")
            }
            Spacer(Modifier.height(8.dp))
            Button(
                onClick = {
                    playForward = false
                    coroutineScope.launch {
                        delay(800)
                        onQuotesClick()
                    }
                }
            ) {
                Text("Show random quote")
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    TwentyFiveDemoTheme {
        MainScreen(
            onQuotesClick = {}
        )
    }
}
