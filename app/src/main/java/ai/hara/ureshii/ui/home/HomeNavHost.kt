package ai.hara.ureshii.ui.home

import ai.hara.ureshii.ui.Screen
import ai.hara.ureshii.ui.main.MainViewModel
import ai.hara.ureshii.ui.playlist.PlaylistScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun HomeNavHost(navController: NavHostController, viewmodel: HomeViewModel) {
    NavHost(navController, startDestination = Screen.None.route) {
        composable(Screen.None.route) { navBackStackEntry ->
        }
        composable(Screen.PlayList.route) {
            PlaylistScreen(viewmodel.playlists[viewmodel.selectedPlaylistIndex])
        }
    }
}