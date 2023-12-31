package ai.hara.ureshii.ui.main

import ai.hara.ureshii.ui.items
import ai.hara.ureshii.ui.theme.ColorNavigation
import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(navController: NavHostController,viewmodel: MainViewModel) {
    Column {
        if (viewmodel.selectedSong.value.mediaId.isNotBlank()){
            BottomPlayer(viewmodel,viewmodel.selectedSong.value)
        }
        BottomNavigation(
            backgroundColor = ColorNavigation,
            contentColor = Color.White
        ) {

            val navBackStackEntry =
                navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry.value?.destination
            items.forEach { screen ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            screen.icon,
                            contentDescription = null
                        )
                    },
                    label = { Text(stringResource(screen.resourceId)) },
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.Gray,
                    onClick = {
                        navigateToScreen(navController, screen.route)
                    }
                )
            }
        }
    }
}

private fun navigateToScreen(bottomNavController: NavHostController, route: String) {
    bottomNavController.navigate(route) {
        popUpTo(bottomNavController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}