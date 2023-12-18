package ai.hara.bnvt.ui.home

import ai.hara.bnvt.R
import ai.hara.bnvt.data.model.Song
import ai.hara.bnvt.ui.main.MainViewModel
import ai.hara.bnvt.util.enums.Status
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.painterResource
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
                Status.SUCCESS -> playSongs(response.data)
                else -> {}
            }
        }
        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    // In Compose world
//                    var name = remember { mutableStateOf(ArrayList<Song>()) }
                    LazyRow {
                        itemsIndexed(homeViewModel.songs) { index, item ->
                            Card(backgroundColor = Color(R.color.purple_200), modifier = Modifier.fillMaxHeight(0.18f).aspectRatio(1.0f).padding(4.dp)) {
                                CardItem(item)
                            }
                        }
                    }
                }
            }
        }
    }

    //
    private fun playSongs(songs: List<Song>?) {
        Log.i("Mohammad", "Song list received:" + (songs?.size ?: 0))
        songs?.let {
            homeViewModel.songs.addAll(it)
        }
    }

    @Composable
    fun CardItem(item: Song) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth(0.3f).padding(2.dp)
        ) {
            val (image, title, artist) = createRefs()
            Image(
                painter = painterResource(id = R.drawable.baseline_library_music_24),
                contentDescription = "", modifier = Modifier.constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }.padding(4.dp))
            Text(text = item.name,color = Color.White, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.constrainAs(title) {
                top.linkTo(image.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }.padding(4.dp))
            Text(color = Color.White, text = item.artist?.get(0)?.name ?: "", modifier = Modifier.constrainAs(artist) {
                top.linkTo(title.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }.padding(4.dp))
        }
    }
}