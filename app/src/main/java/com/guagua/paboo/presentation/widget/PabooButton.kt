package com.guagua.paboo.presentation.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.guagua.paboo.R
import com.guagua.paboo.presentation.theme.ButtonColor

@Preview
@Composable
fun PabooButtonPreview() {
    PabooButton("confirm")
}

@Composable
fun PabooButton(text: String, onClick: (() -> Unit)? = null) {
    Text(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(ButtonColor.Background)
            .clickable { onClick?.invoke() }
            .padding(horizontal = 16.dp, vertical = 4.dp),
        text = text,
        color = ButtonColor.Text
    )
}