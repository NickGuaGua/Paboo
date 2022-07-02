package com.guagua.paboo.presentation.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.airbnb.lottie.model.content.CircleShape
import com.guagua.paboo.R
import com.guagua.paboo.presentation.theme.AppColor
import com.guagua.paboo.presentation.theme.ButtonColor

@Preview
@Composable
fun EmptyContentPreview() {
    Column {
        ErrorContent(
            icon = R.drawable.ic_no_data,
            text = stringResource(id = R.string.error_no_data),
            onRefreshClick = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        ErrorContent(
            icon = R.drawable.ic_no_wifi,
            text = stringResource(id = R.string.error_no_wifi),
            onRefreshClick = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        ErrorContent(
            icon = R.drawable.ic_warning,
            text = stringResource(id = R.string.error_general),
            onRefreshClick = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ErrorContent(
    modifier: Modifier = Modifier,
    icon: Any,
    text: String,
    onRefreshClick: (() -> Unit)? = null
) {
    Box(modifier = modifier) {
        Column(
            modifier = modifier
                .padding(66.dp)
                .widthIn(min = 120.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(100.dp),
                painter = rememberAsyncImagePainter(model = icon),
                contentDescription = null,
                tint = AppColor.TextPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                color = AppColor.TextPrimary,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            onRefreshClick?.let {
                PabooButton(stringResource(id = R.string.refresh)) {
                    it.invoke()
                }
            }
        }
    }
}