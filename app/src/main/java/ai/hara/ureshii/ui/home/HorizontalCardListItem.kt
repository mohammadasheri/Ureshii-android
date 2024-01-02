package ai.hara.ureshii.ui.home

import ai.hara.ureshii.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HorizontalCardListItem(
    title: String,
    subtitle: String,
    pictureUrl: String,
    onClick: () -> Unit
) {
    Card(backgroundColor = colorResource(R.color.card),
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp)
            .width(115.dp)
            .aspectRatio(0.71f)
            .clickable {
                onClick()
            }
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (picture, titleRef, subtitleRef) = createRefs()
            GlideImage(contentScale = ContentScale.Crop,
                model = pictureUrl,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .constrainAs(picture) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
            Text(
                text = title,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .constrainAs(titleRef) {
                        top.linkTo(picture.bottom)
                        start.linkTo(picture.start)
                        end.linkTo(picture.end)
                    }
            )
            Text(
                color = Color.White,
                text = subtitle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis, modifier = Modifier
                    .constrainAs(subtitleRef) {
                        top.linkTo(titleRef.bottom)
                        start.linkTo(picture.start)
                        end.linkTo(picture.end)
                    }
            )
        }
    }
}