package ai.hara.ureshii.ui.playlist

import ai.hara.ureshii.R
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PlaylistScreen() {
    val viewModel: PlaylistViewModel = hiltViewModel()
    Surface(
        color = colorResource(id = R.color.background),
        modifier = Modifier.fillMaxSize()
    ) {

    }
//
//    GlideImage(
//        contentScale = ContentScale.Crop,
//        model = song.mediaMetadata.artworkUri,
//        contentDescription = "",
//        modifier = Modifier
//            .fillMaxSize()
//    )
//
//    Surface(
//        color = Color.Black.copy(alpha = 0.7f),
//        modifier = Modifier.fillMaxSize()
//    ) {
//
//    }
//    ConstraintLayout(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(8.dp)
//    ) {
//        val (title, artist, slider, duration, progress, play, next, previous, repeat, shuffle) = createRefs()
//        Text(
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold,
//            text = song.mediaMetadata.displayTitle.toString(),
//            color = Color.White,
//            maxLines = 1,
//            overflow = TextOverflow.Ellipsis,
//            modifier = Modifier
//                .padding(top = 16.dp)
//                .constrainAs(title) {
//                    top.linkTo(parent.top)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                }
//        )
//        Text(
//            color = Color.White,
//            text = song.mediaMetadata.artist.toString(),
//            maxLines = 1,
//            overflow = TextOverflow.Ellipsis, modifier = Modifier
//                .constrainAs(artist) {
//                    top.linkTo(title.bottom)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                }
//        )
//        Slider(
//            value = if (useNewProgressValue.value) sliderPosition.value else mainViewModel.progress,
//            onValueChange = { newProgress ->
//                useNewProgressValue.value = true
//                sliderPosition.value = newProgress
//                mainViewModel.onPlayerEvent(PlayerEvent.UpdateProgress(newProgress = newProgress))
//            },
//            onValueChangeFinished = {
//                useNewProgressValue.value = false
//            },
//            modifier = Modifier
//                .constrainAs(slider) {
//                    top.linkTo(artist.bottom)
//                    bottom.linkTo(parent.bottom)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                })
//        Text(text = mainViewModel.progressString, color = Color.White,
//            modifier = Modifier
//                .constrainAs(duration) {
//                    top.linkTo(slider.bottom)
//                    start.linkTo(slider.start)
//                })
//        Text(
//            text = mainViewModel.formatDuration(mainViewModel.duration),
//            color = Color.White,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier.constrainAs(progress) {
//                top.linkTo(slider.bottom)
//                end.linkTo(slider.end)
//            }
//        )
//
//        Surface(color = MaterialTheme.colorScheme.primary,
//            shape = RoundedCornerShape(45.dp),
//            modifier = Modifier
//                .padding(top = 32.dp)
//                .constrainAs(play) {
//                    top.linkTo(slider.bottom)
//                    start.linkTo(next.end)
//                    end.linkTo(previous.start)
//                }) {
//            Image(
//                painter = painterResource(
//                    id = if (mainViewModel.isPlaying)
//                        R.drawable.baseline_pause else
//                        R.drawable.baseline_play_arrow_24
//                ),
//                contentDescription = "",
//                colorFilter = ColorFilter.tint(Color.Black),
//                modifier = Modifier
//                    .width(50.dp)
//                    .padding(8.dp)
//                    .aspectRatio(1f)
//                    .clickable {
//                        mainViewModel.onPlayerEvent(PlayerEvent.PlayPause)
//                    }
//            )
//        }
//
//
//        Image(
//            painter = painterResource(id = R.drawable.baseline_skip_next_24),
//            contentDescription = "",
//            modifier = Modifier
//                .padding(top = 32.dp)
//                .width(56.dp)
//                .aspectRatio(1f)
//                .constrainAs(next) {
//                    start.linkTo(repeat.end)
//                    top.linkTo(play.top)
//                    bottom.linkTo(play.bottom)
//                    end.linkTo(play.start)
//                }
//                .clickable {
//                    mainViewModel.onPlayerEvent(PlayerEvent.Forward)
//                }
//        )
//
//        Image(
//            painter = painterResource(id = R.drawable.baseline_skip_previous_24),
//            contentDescription = "",
//            modifier = Modifier
//                .padding(top = 32.dp)
//                .width(56.dp)
//                .aspectRatio(1f)
//                .constrainAs(previous) {
//                    end.linkTo(shuffle.start)
//                    start.linkTo(play.end)
//                    bottom.linkTo(play.bottom)
//                    top.linkTo(play.top)
//                }
//                .clickable {
//                    mainViewModel.onPlayerEvent(PlayerEvent.Backward)
//                }
//        )
//        Image(
//            painter = painterResource(id = R.drawable.baseline_repeat_24),
//            contentDescription = "",
//            modifier = Modifier
//                .padding(top = 32.dp)
//                .width(32.dp)
//                .aspectRatio(1f)
//                .constrainAs(repeat) {
//                    end.linkTo(next.start)
//                    start.linkTo(parent.start)
//                    bottom.linkTo(play.bottom)
//                    top.linkTo(play.top)
//                }
//                .clickable {
//                    mainViewModel.onPlayerEvent(PlayerEvent.Repeat(1))
//                }
//        )
//
//        Image(
//            painter = painterResource(id = R.drawable.baseline_shuffle_24),
//            contentDescription = "",
//            modifier = Modifier
//                .padding(top = 32.dp)
//                .width(32.dp)
//                .aspectRatio(1f)
//                .constrainAs(shuffle) {
//                    end.linkTo(parent.end)
//                    start.linkTo(previous.end)
//                    bottom.linkTo(play.bottom)
//                    top.linkTo(play.top)
//                }
//                .clickable {
//                    mainViewModel.onPlayerEvent(PlayerEvent.Shuffle)
//                }
//        )
//    }
}
