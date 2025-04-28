package com.example.showcase

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.showcase.features.mainlist.ui.MainListPage
import com.example.showcase.features.mainlist.ui.MainListRoute
import com.example.showcase.features.mediadetails.ui.MediaDetailsPage
import com.example.showcase.features.mediadetails.ui.MediaDetailsPageRoute

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
fun Navigation(
    modifier: Modifier,
    snackBarHostState: SnackbarHostState
) {
    val navController = rememberNavController()

    SharedTransitionLayout {
        NavHost(
            navController = navController,
            modifier = modifier,
            startDestination = MainListRoute
        ) {
            composable<MainListRoute> {
                MainListPage(
                    snackBarHostState = snackBarHostState,
                    onNavigateToItemDetails = { mediaId ->
                        navController.navigate(MediaDetailsPageRoute(mediaId = mediaId))
                    },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable
                )
            }

            composable<MediaDetailsPageRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<MediaDetailsPageRoute>()
                MediaDetailsPage(
                    mediaId = route.mediaId,
                    onGoBack = { navController.popBackStack() },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable
                )
            }
        }
    }
}