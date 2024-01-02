package ai.hara.ureshii.ui.playlist

import ai.hara.ureshii.data.model.Playlist
import ai.hara.ureshii.ui.main.MainViewModel
import ai.hara.ureshii.ui.theme.ColorNavigation
import ai.hara.ureshii.util.getHostURL
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun PlaylistScreen(playlist: Playlist, mainViewModel: MainViewModel) {
    val listState = rememberLazyListState()
    val headerHeight = 275.dp
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorNavigation)
    ) {
        Header(playlist, listState, headerHeight)
        Body(playlist.id, listState, headerHeight, mainViewModel)
        Toolbar(listState, headerHeight)
    }
}

@Composable
private fun Body(
    pId: Int,
    listState: LazyListState,
    headerHeight: Dp,
    mainViewModel: MainViewModel
) {
    val viewModel: PlaylistViewModel = hiltViewModel()
    LazyColumn(
        state = listState,
        modifier = Modifier
    ) {
        item { Spacer(Modifier.height(headerHeight)) }

        itemsIndexed(viewModel.songs) { index, item ->
            val picture = "${getHostURL()}song/picture/download/${item.id}"
            VerticalListItem(title = item.name, subtitle = item.name, pictureUrl = picture, index) {
                mainViewModel.loadData(viewModel.songs, index)
            }
        }
    }
    DisposableEffect(Unit) {
        viewModel.loadData(pId)
        onDispose {}
    }
}