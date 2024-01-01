package ai.hara.ureshii.ui.playlist

import ai.hara.ureshii.R
import ai.hara.ureshii.data.model.Playlist
import ai.hara.ureshii.ui.theme.ColorNavigation
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

private val headerHeight = 275.dp
private val toolbarHeight = 56.dp

@Composable
fun PlaylistScreen(playlist: Playlist) {
    val scroll: ScrollState = rememberScrollState(0)
    val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.toPx() }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorNavigation)
    ) {
        Header(playlist, scroll, headerHeight)
        Body(scroll)
        Toolbar(scroll, headerHeightPx, toolbarHeightPx)
    }
}

@Composable
private fun Body(scroll: ScrollState) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(scroll)
            .fillMaxWidth()
    ) {
        Spacer(Modifier.height(headerHeight))

        repeat(5) {
            Text(
                text = stringResource(R.string.lorem_ipsum),
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0XFF161616))
                    .padding(16.dp)
            )
        }
    }
}