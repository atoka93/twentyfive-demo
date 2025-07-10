package com.attilaszabo.twentyfivedemo.features.cutofftime.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object CutOffTimeRoute

fun NavController.navigateToCutOffTime(navOptions: NavOptions? = null) {
    navigate(CutOffTimeRoute, navOptions)
}

fun NavGraphBuilder.cutOffTimeScreen(
    onBackClick: () -> Unit,
) {
    composable<CutOffTimeRoute> {
        CutOffTimeScreen(
            onBackClick = onBackClick,
        )
    }
}
