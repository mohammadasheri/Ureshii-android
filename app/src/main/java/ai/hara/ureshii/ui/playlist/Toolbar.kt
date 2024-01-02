package ai.hara.ureshii.ui.playlist

import ai.hara.ureshii.ui.theme.ColorBottomPlayer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val toolbarHeight = 56.dp

@Composable
fun Toolbar(state: LazyListState, headerHeight: Dp) {
    val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.toPx() }
    val toolbarBottom = headerHeightPx - toolbarHeightPx
    val scrollValue = remember { mutableIntStateOf(0) }
    val showToolbar = remember {
        derivedStateOf {
            scrollValue.intValue = state.layoutInfo.visibleItemsInfo.firstOrNull()?.size ?: 0
            val totalItems = state.layoutInfo.totalItemsCount
            val itemLengthInPx = state.layoutInfo.visibleItemsInfo.firstOrNull()?.size ?: 0
            val totalLengthInPx = totalItems * itemLengthInPx
            if (totalLengthInPx > 0) {
                val scrollPos = (state.firstVisibleItemIndex * itemLengthInPx) / totalLengthInPx
                (state.firstVisibleItemScrollOffset + scrollPos >= toolbarBottom) || state.firstVisibleItemIndex > 0
            } else {
                false
            }
        }
    }

    AnimatedVisibility(
        visible = showToolbar.value,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300))
    ) {

        TopAppBar(
            modifier = Modifier.background(ColorBottomPlayer),
            navigationIcon = {
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            },
            title = { Text(text = "Mohammad") },
            backgroundColor = Color.Transparent,
            elevation = 0.dp
        )
    }
}