package ai.hara.ureshii.ui.main

import ai.hara.ureshii.R
import ai.hara.ureshii.service.PlayerEvent
import ai.hara.ureshii.ui.theme.ColorBottomPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.media3.common.MediaItem
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.smarttoolfactory.slider.ColorfulSlider
import com.smarttoolfactory.slider.MaterialSliderDefaults
import com.smarttoolfactory.slider.SliderBrushColor

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
fun BottomPlayer(mainViewModel: MainViewModel, mediaItem: MediaItem) {
    ConstraintLayout(
        modifier = Modifier
            .background(ColorBottomPlayer)
            .fillMaxWidth()
            .height(50.dp).clickable {
                mainViewModel.showPlayerView = true
            }
    ) {
        val (picture, slider, play, name, artist) = createRefs()
        Image(
            painter = painterResource(
                id = if (mainViewModel.isPlaying)
                    R.drawable.baseline_pause else
                    R.drawable.baseline_play_arrow_24
            ),
            contentDescription = "",
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier.padding(end=16.dp)
                .clickable {
                    mainViewModel.onPlayerEvent(PlayerEvent.PlayPause)
                }.constrainAs(play){
                    top.linkTo(parent.top)
                    bottom.linkTo(slider.top)
                    end.linkTo(slider.end)
                }
        )
        GlideImage(
            contentScale = ContentScale.Crop,
            model = mediaItem.mediaMetadata.artworkUri,
            contentDescription = "",
            modifier = Modifier.aspectRatio(1f).constrainAs(picture){
                top.linkTo(parent.top)
                bottom.linkTo(slider.top)
                start.linkTo(parent.start)
            }
        )
        Text(text = mediaItem.mediaMetadata.displayTitle.toString(),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start=16.dp)
                .constrainAs(name) {
                    top.linkTo(picture.top)
                    bottom.linkTo(artist.top)
                    start.linkTo(picture.end)
                })
        Text(text = mediaItem.mediaMetadata.artist.toString(),
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(start=16.dp)
                .constrainAs(artist) {
                    top.linkTo(name.bottom)
                    bottom.linkTo(picture.bottom)
                    start.linkTo(picture.end)
                })
        ColorfulSlider(
            value = 0.5f,
            onValueChange = { progress -> },
            modifier = Modifier
                .height(2.dp)
                .padding(start = 16.dp, end = 16.dp)
                .constrainAs(slider) {
                    bottom.linkTo(parent.bottom)
                },
            trackHeight = 2.dp,
            thumbRadius = 0.dp,
            colors = MaterialSliderDefaults.materialColors(
                thumbColor = SliderBrushColor(Color.Yellow),
                activeTrackColor = SliderBrushColor(Color.White),
                inactiveTrackColor = SliderBrushColor(Color.Gray)
            ),
        )
    }
}