package components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.layout.ContentScale
import com.seiko.imageloader.cache.CachePolicy
import com.seiko.imageloader.model.ImageAction
import com.seiko.imageloader.model.ImageRequest
import com.seiko.imageloader.model.ImageRequestBuilder
import com.seiko.imageloader.rememberImageSuccessPainter
import com.seiko.imageloader.ui.AutoSizeBox

@Composable
internal fun AsyncImage(
    url: String, modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    block: (ImageRequestBuilder.() -> Unit)? = null,
) {
    Box(modifier, Alignment.Center) {
        val dataState by rememberUpdatedState(url)
        val blockState by rememberUpdatedState(block)
        val request by remember {
            derivedStateOf {
                ImageRequest {
                    data(dataState)
                    options {
                        maxImageSize = 1024
                        memoryCachePolicy = CachePolicy.ENABLED
                    }
                    blockState?.invoke(this)
                }
            }
        }

        AutoSizeBox(
            request,
            Modifier.matchParentSize(),
        ) { action ->
            when (action) {
                is ImageAction.Loading -> {
                    CircularProgressIndicator()
                }

                is ImageAction.Success -> {
                    Image(
                        rememberImageSuccessPainter(action),
                        contentDescription = "image",
                        contentScale = contentScale,
                        modifier = Modifier.matchParentSize(),
                    )
                }

                is ImageAction.Failure -> {
                    Text(action.error.message ?: "Error")
                }
            }
        }
    }
}