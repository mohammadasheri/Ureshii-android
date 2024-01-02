package ai.hara.ureshii.ui.playlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun VerticalListItem(
    title: String,
    subtitle: String,
    pictureUrl: String,
    index: Int,
    onClick: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        val (indexRef, picture, titleRef, subtitleRef) = createRefs()
        Text(
            text = (index+1).toString(),
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1,
            fontSize = 14.sp,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(start = 12.dp)
                .constrainAs(indexRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }
        )
        GlideImage(contentScale = ContentScale.Crop,
            model = pictureUrl,
            contentDescription = "",
            modifier = Modifier
                .padding(top = 12.dp, start = 12.dp, end = 12.dp)
                .aspectRatio(1f)
                .constrainAs(picture) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(indexRef.end)
                })
        Text(
            text = title,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = 12.dp)
                .constrainAs(titleRef) {
                    top.linkTo(picture.top)
                    start.linkTo(picture.end)
                    bottom.linkTo(subtitleRef.top)
                }
        )
        Text(
            color = MaterialTheme.colorScheme.secondary,
            text = subtitle,
            maxLines = 1,
            fontSize = 12.sp,
            overflow = TextOverflow.Ellipsis, modifier = Modifier
                .padding(bottom = 8.dp)
                .constrainAs(subtitleRef) {
                    top.linkTo(titleRef.bottom)
                    start.linkTo(picture.end)
                    bottom.linkTo(picture.bottom)
                }
        )
    }
}