package com.attilaszabo.twentyfivedemo

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.attilaszabo.twentyfivedemo.ui.MainScreen
import kotlinx.serialization.Serializable

@Serializable
data object MainRoute

fun NavController.navigateToMain(navOptions: NavOptions? = null) {
    navigate(MainRoute, navOptions)
}

fun NavGraphBuilder.mainScreen(
    onQuotesClick: () -> Unit,
) {
    composable<MainRoute> {
        MainScreen(
            onQuotesClick = onQuotesClick,
        )
    }
}
