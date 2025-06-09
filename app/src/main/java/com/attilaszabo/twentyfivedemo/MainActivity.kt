package com.attilaszabo.twentyfivedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.attilaszabo.twentyfivedemo.quotepresentation.ui.QuotesScreen
import com.attilaszabo.twentyfivedemo.ui.MainScreen
import com.attilaszabo.twentyfivedemo.ui.theme.TwentyFiveDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TwentyFiveDemoTheme {
                // Set up the navigation controller
                val navController = rememberNavController()

                // Main Content with Navigation
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") {
                        MainScreen(navController)
                    }
                    composable("quotes") {
                        QuotesScreen()
                    }
                }
            }
        }
    }
}
