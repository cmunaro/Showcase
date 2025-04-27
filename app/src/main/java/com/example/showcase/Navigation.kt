package com.example.showcase

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.showcase.features.mainlist.ui.MainListPage
import com.example.showcase.features.mainlist.ui.MainListRoute
import com.example.showcase.features.mediadetails.MediaDetailsPage
import com.example.showcase.features.mediadetails.MediaDetailsPageRoute

@Composable
fun Navigation(
    modifier: Modifier,
    snackBarHostState: SnackbarHostState
) {
    val navController = rememberNavController()

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
                }
            )
        }

        composable<MediaDetailsPageRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<MediaDetailsPageRoute>()
            MediaDetailsPage(mediaId = route.mediaId)
        }
    }
}