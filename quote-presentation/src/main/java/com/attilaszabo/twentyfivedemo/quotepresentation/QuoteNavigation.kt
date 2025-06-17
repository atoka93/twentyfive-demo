package com.attilaszabo.twentyfivedemo.quotepresentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.attilaszabo.twentyfivedemo.quotepresentation.ui.QuoteScreen
import kotlinx.serialization.Serializable

//@Serializable
//data object QuoteBaseRoute
//
//fun NavGraphBuilder.quoteSection(
//    onTopicClick: (String) -> Unit,
//    topicDestination: NavGraphBuilder.() -> Unit,
//) {
//    navigation<QuoteBaseRoute>(startDestination = QuoteRoute) {
//
//    }
//}

@Serializable
data object QuoteRoute

fun NavController.navigateToQuote(navOptions: NavOptions? = null) {
    navigate(QuoteRoute, navOptions)
}

fun NavGraphBuilder.quoteScreen(
    onBackClick: () -> Unit,
) {
    composable<QuoteRoute> {
        QuoteScreen(
            onBackClick = onBackClick,
        )
    }
}
