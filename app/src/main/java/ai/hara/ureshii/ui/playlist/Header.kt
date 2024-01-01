package ai.hara.ureshii.ui.playlist

import ai.hara.ureshii.data.model.Playlist
import ai.hara.ureshii.util.getHostURL
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Header(playlist: Playlist, scroll: ScrollState, headerHeight: Dp) {
    val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(headerHeight)
            .graphicsLayer {
                alpha = (-1f / headerHeightPx) * scroll.value + 1
                translationY = -scroll.value.toFloat() / 2f // Parallax effect
            }
    ) {
        GlideImage(
            contentScale = ContentScale.FillBounds,
            model = "${getHostURL()}playlist/picture/download/${playlist.id}",
            contentDescription = "item picture"
        )
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0xAA000000)),
                    )
                )
        )

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (title, subTitle) = createRefs()
            Text(text = playlist.name, color = MaterialTheme.colorScheme.primary,fontSize = 20.sp, modifier = Modifier.constrainAs(title) {
                bottom.linkTo(subTitle.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
            Text(text = playlist.name, color = MaterialTheme.colorScheme.primary, fontSize = 14.sp, modifier = Modifier.padding(top=8.dp, bottom = 16.dp).constrainAs(subTitle) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
        }
    }
}