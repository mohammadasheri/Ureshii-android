package ai.hara.ureshii.ui.home

import ai.hara.ureshii.R
import ai.hara.ureshii.ui.Screen
import ai.hara.ureshii.ui.main.MainViewModel
import ai.hara.ureshii.util.getHostURL
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel
) {
        val viewModel: HomeViewModel = hiltViewModel()
        val navController = rememberNavController()
    if (!viewModel.isLoggedIn) {
        mainViewModel.isLoggedIn = viewModel.isLoggedIn
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background)),
    ) {
        item {
            HomePlayLists(navController, mainViewModel, viewModel)
        }
        item {
            HomeSongList(mainViewModel, viewModel)
        }
    }
    HomeNavHost(navController = navController, viewModel, mainViewModel)
    DisposableEffect(Unit) {
        viewModel.loadData()
        onDispose {}
    }
}


@Composable
fun HomePlayLists(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    viewmodel: HomeViewModel
) {
    Text(
        text = stringResource(R.string.new_playlists),
        color = colorResource(id = R.color.white),
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 12.dp, bottom = 8.dp)
    )
    LazyRow {
        itemsIndexed(viewmodel.playlists) { index, item ->
            HorizontalCardListItem(
                item.name,
                item.name,
                "${getHostURL()}playlist/picture/download/${item.id}"
            ) {
                viewmodel.selectedPlaylistIndex = index
                mainViewModel.navigateToScreen(navController, Screen.PlayList.route)
            }
        }
    }
}

@Composable
fun HomeSongList(mainViewModel: MainViewModel, viewmodel: HomeViewModel) {
    Text(
        text = stringResource(R.string.new_songs),
        color = colorResource(id = R.color.white),
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 12.dp, top = 16.dp, bottom = 8.dp)
    )
    LazyRow {
        itemsIndexed(viewmodel.songs) { index, item ->
            val artist = ""
            if (item.artist.isNotEmpty()){
                item.artist.first().name
            }
            val picture = "${getHostURL()}song/picture/download/${item.id}"
            HorizontalCardListItem(item.name, artist, picture) {
                mainViewModel.loadData(viewmodel.songs, index)
            }
        }
    }
}

