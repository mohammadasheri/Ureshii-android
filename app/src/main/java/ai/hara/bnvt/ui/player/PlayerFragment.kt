package ai.hara.bnvt.ui.player

import ai.hara.bnvt.ui.main.MainViewModel
import ai.hara.bnvt.util.enums.Status
import ai.hara.bnvt.util.getHostURL
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.style.TextOverflow
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlayerFragment : Fragment() {

    companion object {
        fun newInstance() = PlayerFragment()
    }


    private lateinit var playerViewModel: PlayerViewModel
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        playerViewModel = ViewModelProvider(this)[PlayerViewModel::class.java]
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        playerViewModel.getSongs().observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Status.ERROR -> mainViewModel.isLoggedOut.value = true
                Status.SUCCESS -> playerViewModel.songs.addAll(response.data!!)
                else -> {}
            }
        }
        playerViewModel.getPlaylists().observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Status.ERROR -> mainViewModel.isLoggedOut.value = true
                Status.SUCCESS -> playerViewModel.playlists.addAll(response.data!!)
                else -> {}
            }
        }
        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    PlayerScreen(
                        "${getHostURL()}song/picture/download/${mainViewModel.selectedSong.value?.id}",
                        mainViewModel.selectedSong.value?.name ?: "",
                        mainViewModel.selectedSong.value?.name ?: ""
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    fun PlayerScreen(imageUrl: String, titleString: String, subtitleString: String) {
        GlideImage(
            contentScale = ContentScale.Crop,
            model = imageUrl,
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
        )
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (image, title, artist) = createRefs()
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