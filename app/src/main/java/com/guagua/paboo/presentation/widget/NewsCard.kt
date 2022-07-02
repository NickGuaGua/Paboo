package com.guagua.paboo.presentation.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.guagua.paboo.data.model.Article
import com.guagua.paboo.presentation.theme.AppColor
import timber.log.Timber

@Composable
fun NewsCard(article: Article, onClick: ((Article) -> Unit)? = null) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.2f)
            .shadow(2.dp, RoundedCornerShape(16.dp), true)
            .background(Color.White)
            .clickable { onClick?.invoke(article) }
            .padding(18.dp)
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.7f)
                .clip(RoundedCornerShape(8.dp)),
            model = ImageRequest.Builder(LocalContext.current)
                .data(article.urlToImage)
                .memoryCacheKey(article.id)
                .diskCacheKey(article.id)
                .crossfade(true)
                .build(),
            loading = {
                Box(modifier = Modifier.fillMaxWidth().height(180.dp)) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(56.dp).align(Alignment.Center)
                    )
                }
            },
            contentDescription = "article image",
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = article.source.name, style = MaterialTheme.typography.body1, color = AppColor.TextSecondary)
        Text(text = article.title, fontWeight = FontWeight.Bold, color = AppColor.TextPrimary, overflow = TextOverflow.Ellipsis)
    }
}