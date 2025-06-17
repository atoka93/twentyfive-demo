package com.attilaszabo.twentyfivedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.attilaszabo.twentyfivedemo.quotepresentation.navigateToQuote
import com.attilaszabo.twentyfivedemo.quotepresentation.quoteScreen
import com.attilaszabo.twentyfivedemo.quotepresentation.ui.QuoteScreen
import com.attilaszabo.twentyfivedeom.commonpresentation.DataState
import com.attilaszabo.twentyfivedeom.commonpresentation.theme.TwentyFiveDemoTheme

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
                    )
                    quoteScreen(
                        onBackClick = navController::popBackStack
                    )
                }
            }
        }
    }
}
