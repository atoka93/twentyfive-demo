package com.attilaszabo.twentyfivedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.attilaszabo.twentyfivedemo.common.presentation.theme.TwentyFiveDemoTheme
import com.attilaszabo.twentyfivedemo.features.cutofftime.presentation.cutOffTimeScreen
import com.attilaszabo.twentyfivedemo.features.cutofftime.presentation.navigateToCutOffTime
import com.attilaszabo.twentyfivedemo.quote.presentation.navigateToQuote
import com.attilaszabo.twentyfivedemo.quote.presentation.quoteScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TwentyFiveDemoTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = MainRoute
                ) {
                    mainScreen(
                        onQuotesClick = navController::navigateToQuote,
                        onRemainingTimeClick = navController::navigateToCutOffTime,
                    )
                    quoteScreen(
                        onBackClick = navController::popBackStack
                    )
                    cutOffTimeScreen(
                        onBackClick = navController::popBackStack
                    )
                }
            }
        }
    }
}
