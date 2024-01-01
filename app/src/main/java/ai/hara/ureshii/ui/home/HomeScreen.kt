package ai.hara.ureshii.ui.home

import ai.hara.ureshii.R
import ai.hara.ureshii.data.model.Playlist
import ai.hara.ureshii.data.model.Song
import ai.hara.ureshii.ui.Screen
import ai.hara.ureshii.ui.main.MainViewModel
import ai.hara.ureshii.util.getHostURL
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

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
    HomeNavHost(navController = navController,viewModel)
    DisposableEffect(Unit) {
        viewModel.getSongs()
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
            PlaylistItem(navController, mainViewModel, viewmodel, item,index)
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
            SongItem(
                viewmodel,
                mainViewModel,
                item,
                index
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PlaylistItem(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    viewmodel: HomeViewModel,
    item: Playlist,
    index: Int
) {
    Card(backgroundColor = colorResource(R.color.card),
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp)
            .width(115.dp)
            .aspectRatio(0.71f)
            .clickable {
                viewmodel.selectedPlaylistIndex = index
                mainViewModel.navigateToScreen(navController, Screen.PlayList.route)
            }
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (image, title, artist) = createRefs()
            GlideImage(contentScale = ContentScale.Crop,
                model = "${getHostURL()}playlist/picture/download/${item.id}",
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
            Text(
                text = item.name,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(image.bottom)
                        start.linkTo(image.start)
                        end.linkTo(image.end)
                    }
            )
            Text(
                color = Color.White,
                text = item.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis, modifier = Modifier
                    .constrainAs(artist) {
                        top.linkTo(title.bottom)
                        start.linkTo(image.start)
                        end.linkTo(image.end)
                    }
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SongItem(viewmodel: HomeViewModel, mainViewModel: MainViewModel, item: Song, index: Int) {
    Card(backgroundColor = colorResource(R.color.card),
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp)
            .width(115.dp)
            .aspectRatio(0.71f)
            .clickable {
                mainViewModel.loadData(viewmodel.songs, index)
            }
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (image, title, artist) = createRefs()
            GlideImage(contentScale = ContentScale.FillBounds,
                model = "${getHostURL()}song/picture/download/${item.id}",
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
            Text(
                text = item.name,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(image.bottom)
                        start.linkTo(image.start)
                        end.linkTo(image.end)
                    }
            )
            Text(
                color = Color.White,
                text = item.artist?.get(0)?.name ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis, modifier = Modifier
                    .constrainAs(artist) {
                        top.linkTo(title.bottom)
                        start.linkTo(image.start)
                        end.linkTo(image.end)
                    }
            )
        }
    }
}
