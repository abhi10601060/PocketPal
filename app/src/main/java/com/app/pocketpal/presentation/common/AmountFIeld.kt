package com.app.pocketpal.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.size.Size
import com.app.pocketpal.presentation.ui.theme.LightAccent
import com.app.pocketpal.presentation.ui.theme.PocketPalTheme
import com.app.pocketpal.presentation.ui.theme.ThemeColor

@Composable
fun AmountTextField(modifier: Modifier = Modifier,fontSize: TextUnit = 18.sp, value: String, onValueChange: (String) -> Unit = {}, readOnly: Boolean = false) {
    TextField(value =  value,
        onValueChange = {onValueChange(it)},
        placeholder = {Text(text = "0", fontSize = fontSize, fontWeight = FontWeight.Bold, color = Color.Gray)},
        modifier = modifier
                .widthIn(min = 100.dp , max = 160.dp)
                .padding(vertical = (fontSize.value/2).dp, horizontal = (fontSize.value/2).dp)
                .clip(shape = RoundedCornerShape(10.dp)),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = LightAccent,
            unfocusedContainerColor = LightAccent,
            disabledContainerColor = LightAccent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = ThemeColor
        ),
        singleLine = true,
        trailingIcon = {Text(text = "₹", fontSize = (fontSize.value + 4f).sp, fontWeight = FontWeight.Bold)},
        textStyle = TextStyle(fontSize = fontSize, fontWeight = FontWeight.Bold),
        readOnly = readOnly,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
}


@Preview
@Composable
private fun AmountTextFieldPrev() {
    PocketPalTheme(darkTheme = false) {
        Box(modifier = Modifier.fillMaxSize().background(color = Color.White), contentAlignment = Alignment.Center) {
            AmountTextField(value = "1000000", fontSize = 18.sp,)
        }
    }
}