package ai.hara.bnvt.ui.home

import ai.hara.bnvt.R
import ai.hara.bnvt.ui.main.MainViewModel
import ai.hara.bnvt.util.enums.Status
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

        homeViewModel.getSongs().observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Status.ERROR -> mainViewModel.isLoggedOut.value = true
                Status.SUCCESS -> homeViewModel.songs.addAll(response.data!!)
                else -> {}
            }
        }
        homeViewModel.getPlaylists().observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Status.ERROR -> mainViewModel.isLoggedOut.value = true
                Status.SUCCESS -> homeViewModel.playlists.addAll(response.data!!)
                else -> {}
            }
        }
        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    Column {
//                        HomePlayLists()
                        HomeSongList()
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
            modifier = Modifier.padding(start = 12.dp, bottom = 4.dp)
        )
        LazyRow {
            itemsIndexed(homeViewModel.playlists) { index, item ->
                PlaylistCardItem(item.name, item.name, index)
            }
        }
    }

    @Composable
    fun HomeSongList() {
        Text(
            text = stringResource(R.string.new_songs),
            color = colorResource(id = R.color.white),
            modifier = Modifier.padding(start = 12.dp, bottom = 4.dp)
        )
        LazyRow {
            itemsIndexed(homeViewModel.songs) { index, item ->
                SongItem(item.name, item.artist?.get(0)?.name ?: "", index)
            }
        }
    }

    @Composable
    fun PlaylistCardItem(titleString: String, subtitleString: String, index: Int) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .padding(2.dp)
        ) {
            val (image1, title1, artist1) = createRefs()
            Card(
                elevation = 4.dp,
                backgroundColor = colorResource(id = R.color.card),
                modifier = Modifier
                    .constrainAs(image1) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxHeight(0.18f)
                    .aspectRatio(1.0f)
                    .padding(4.dp)
                    .clickable {
                        mainViewModel.loadData(homeViewModel.songs, index)
                    }
            ) {}
            Text(
                text = titleString,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .constrainAs(title1) {
                        top.linkTo(image1.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(4.dp)
            )
            Text(
                color = Color.White,
                text = subtitleString,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis, modifier = Modifier
                    .constrainAs(artist1) {
                        top.linkTo(image1.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(4.dp)
            )
        }
    }

    @Composable
    fun SongItem(titleString: String, subtitleString: String, index: Int) {
        ConstraintLayout(
            modifier = Modifier.background(Color.Green)
                .fillMaxHeight(0.2f)
                .aspectRatio(1.0f)
        ) {
            val (image, title, artist) = createRefs()
            Card(
                backgroundColor = colorResource(id = R.color.red),
                modifier = Modifier
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxHeight()
                    .aspectRatio(1.0f)
                    .clickable {
                        mainViewModel.loadData(homeViewModel.songs, index)
                    }
            ) {}
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
                    .padding(4.dp)
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
                    .padding(4.dp)
            )
        }
    }
}