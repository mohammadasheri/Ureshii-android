package ai.hara.ureshii.ui.home

import ai.hara.ureshii.R
import ai.hara.ureshii.ui.main.MainViewModel
import ai.hara.ureshii.util.enums.Status
import ai.hara.ureshii.util.getHostURL
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {


    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

//        homeViewModel.getSongs().observe(viewLifecycleOwner) { response ->
//            when (response.status) {
//                Status.ERROR -> mainViewModel.isLoggedOut.value = true
//                Status.SUCCESS -> homeViewModel.songs.addAll(response.data!!)
//                else -> {}
//            }
//        }
//        homeViewModel.getPlaylists().observe(viewLifecycleOwner) { response ->
//            when (response.status) {
//                Status.ERROR -> mainViewModel.isLoggedOut.value = true
//                Status.SUCCESS -> homeViewModel.playlists.addAll(response.data!!)
//                else -> {}
//            }
//        }
        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    LazyColumn {
                        item {
                            HomePlayLists()
                        }
                        item {
                            HomeSongList()
                        }
                    }
                    DisposableEffect(Unit) {
                        homeViewModel.getSongs()
                        onDispose {}
                    }
                }
            }
        }
    }

    @Composable
    fun HomePlayLists() {
        Text(
            text = stringResource(R.string.new_playlists),
            color = colorResource(id = R.color.white),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 12.dp, bottom = 8.dp)
        )
        LazyRow {
            itemsIndexed(homeViewModel.playlists) { index, item ->
                PlaylistItem(
                    "${getHostURL()}playlist/picture/download/${item.id}",
                    item.name,
                    item.name,
                    index
                )
            }
        }
    }

    @Composable
    fun HomeSongList() {
        Text(
            text = stringResource(R.string.new_songs),
            color = colorResource(id = R.color.white),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 12.dp, top=16.dp, bottom = 8.dp)
        )
        LazyRow {
            itemsIndexed(homeViewModel.songs) { index, item ->
                SongItem(
                    "${getHostURL()}song/picture/download/${item.id}",
                    item.name,
                    item.artist?.get(0)?.name ?: "",
                    index
                )
            }
        }
    }

    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    fun PlaylistItem(imageUrl: String, titleString: String, subtitleString: String, index: Int) {
        Card(backgroundColor = colorResource(R.color.card),
            modifier = Modifier
                .padding(start = 4.dp, end = 4.dp)
                .width(115.dp)
                .aspectRatio(0.71f)
                .clickable {
                    mainViewModel.loadData(homeViewModel.songs, index)
                }
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (image, title, artist) = createRefs()
                GlideImage(contentScale = ContentScale.Crop,
                    model = imageUrl,
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
                    text = titleString,
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
                    text = subtitleString,
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
    fun SongItem(imageUrl: String, titleString: String, subtitleString: String, index: Int) {
        Card(backgroundColor = colorResource(R.color.card),
            modifier = Modifier
                .padding(start = 4.dp, end = 4.dp)
                .width(115.dp)
                .aspectRatio(0.71f)
                .clickable {
                    mainViewModel.loadData(homeViewModel.songs, index)
                }
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (image, title, artist) = createRefs()
                GlideImage(contentScale = ContentScale.FillBounds,
                    model = imageUrl,
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
                    text = titleString,
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
                    text = subtitleString,
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

}