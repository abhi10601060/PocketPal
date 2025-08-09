package com.app.pocketpal.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.pocketpal.presentation.ui.theme.LightAccent
import com.app.pocketpal.presentation.ui.theme.PocketPalTheme
import com.app.pocketpal.presentation.ui.theme.ThemeColor

@Composable
fun PocketPalTextField(modifier: Modifier = Modifier, value: String, onValueChange: (String) -> Unit = {}, singleLine: Boolean = true, minLines: Int = 1, readOnly: Boolean = false) {
    TextField(value =  value,
        onValueChange = {onValueChange(it)},
        modifier = modifier.clip(shape = RoundedCornerShape(10.dp)),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = LightAccent,
            unfocusedContainerColor = LightAccent,
            disabledContainerColor = LightAccent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = ThemeColor
        ),
        singleLine = singleLine,
        minLines = minLines,
        readOnly = readOnly
    )
}


@Preview
@Composable
private fun PocketPalTextFieldPrev() {
    PocketPalTheme(darkTheme = false) {
        Box(modifier = Modifier.fillMaxSize().background(color = Color.White), contentAlignment = Alignment.Center) {
            PocketPalTextField(value = "")
        }
    }
}