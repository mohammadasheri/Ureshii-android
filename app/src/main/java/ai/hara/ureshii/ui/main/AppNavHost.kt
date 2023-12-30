package ai.hara.ureshii.ui.main

import ai.hara.ureshii.ui.Screen
import ai.hara.ureshii.ui.home.HomeScreen
import ai.hara.ureshii.ui.home.HomeViewModel
import ai.hara.ureshii.ui.library.LibraryScreen
import ai.hara.ureshii.ui.player.PlayerScreen
import ai.hara.ureshii.ui.player.PlayerViewModel
import ai.hara.ureshii.ui.search.SearchScreen
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun BottomNavHost(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    homeViewModel: HomeViewModel,
    innerPadding: PaddingValues
) {
    NavHost(
        navController,
        startDestination = Screen.Home.route,
        Modifier.padding(innerPadding)
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                navController,
                mainViewModel,
                homeViewModel
            )
        }
        composable(Screen.Search.route) { SearchScreen(navController) }
        composable(Screen.Library.route) { LibraryScreen(navController) }
    }
}

@Composable
fun MainNavHost(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    playerViewModel: PlayerViewModel
) {
    NavHost(navController, startDestination = Screen.None.route) {
        composable(Screen.Player.route) {
            PlayerScreen(
                navController,
                mainViewModel,
                playerViewModel
            )
        }
        composable(Screen.None.route) {
            mainViewModel.showPlayerView = false
        }
    }
}