package ai.hara.ureshii.ui

import ai.hara.ureshii.R
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Home : Screen("home", R.string.title_home, Icons.Filled.Home)
    object Search : Screen("search", R.string.title_search, Icons.Filled.Search)
    object Library : Screen("library", R.string.title_library, Icons.Filled.Face)
    object Player : Screen("player", R.string.title_player, Icons.Filled.PlayArrow)
}

val items = listOf(
    Screen.Home,
    Screen.Search,
    Screen.Library,
)
