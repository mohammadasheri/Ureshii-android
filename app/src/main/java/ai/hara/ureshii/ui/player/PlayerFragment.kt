package ai.hara.ureshii.ui.player

import ai.hara.ureshii.ui.main.MainViewModel
import ai.hara.ureshii.ui.main.UIEvent
import ai.hara.ureshii.util.enums.Status
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.MediaItem
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

        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    PlayerScreen(
                        mainViewModel.selectedSong.value
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    fun PlayerScreen(song: MediaItem) {
        val sliderPosition = remember { mutableStateOf(0f) }
        val useNewProgressValue = remember { mutableStateOf(false) }

        GlideImage(
            contentScale = ContentScale.Crop,
            model = song.mediaMetadata.artworkUri,
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
        )

        Surface(
            color = Color.Black.copy(alpha = 0.7f),
            modifier = Modifier.fillMaxSize()
        ) {

        }
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (title, artist, slider, duration, progress) = createRefs()
            Text(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                text = song.mediaMetadata.displayTitle.toString(),
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
            Text(
                color = Color.White,
                text = song.mediaMetadata.artist.toString(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis, modifier = Modifier
                    .constrainAs(artist) {
                        top.linkTo(title.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
            Slider(
                value = if (useNewProgressValue.value) sliderPosition.value else mainViewModel.progress,
                onValueChange = { newProgress ->
                    useNewProgressValue.value = true
                    sliderPosition.value = newProgress
                    mainViewModel.onUIEvent(UIEvent.UpdateProgress(newProgress = newProgress))
                },
                onValueChangeFinished = {
                    useNewProgressValue.value = false
                },
                modifier = Modifier
                    .padding(8.dp)
                    .constrainAs(slider) {
                        top.linkTo(artist.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
            Text(text = mainViewModel.progressString, color = Color.White,
                modifier = Modifier.constrainAs(duration) {
                    top.linkTo(slider.bottom)
                    start.linkTo(slider.start)
                })
            Text(
                text = mainViewModel.formatDuration(mainViewModel.duration),
                color = Color.White,
                modifier = Modifier.constrainAs(progress) {
                    top.linkTo(slider.bottom)
                    end.linkTo(slider.end)
                }
            )
        }
    }
}