package ai.hara.ureshii.ui

import ai.hara.ureshii.R
import androidx.annotation.StringRes

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Home : Screen("home", R.string.title_home)
    object Search : Screen("search", R.string.title_search)
    object Library : Screen("library", R.string.title_library)
    object Player : Screen("player", R.string.title_player)
}

val items = listOf(
    Screen.Home,
    Screen.Search,
    Screen.Library,
)
