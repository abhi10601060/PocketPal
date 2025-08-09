package com.app.pocketpal.presentation.common

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.pocketpal.domain.model.Label
import com.app.pocketpal.presentation.ui.theme.LightAccent
import com.app.pocketpal.presentation.ui.theme.PocketPalTheme
import com.app.pocketpal.presentation.ui.theme.ThemeColor


@Composable
fun LabelDropdown(modifier: Modifier = Modifier, labels: List<Label>, fontSize : TextUnit = 15.sp, value: Label? = null, onLabelSelected : (label : Label) -> Unit) {

    var color = if(value == null) Color.Cyan else value.color
    var bgColor = color.copy(alpha = 0.2f)

    var isExpanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Box(modifier = modifier.wrapContentSize()
            .clip(RoundedCornerShape(10.dp))
            .background(color = bgColor)
            .padding((fontSize.value/2).dp)
            .clickable{isExpanded = true},
            contentAlignment = Alignment.Center
        ){
            Text(text = if (value == null) "Select Label" else value.name, fontSize = fontSize, color= color)
        }

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
            },
            modifier = Modifier.background(color = LightAccent)
        ) {
            labels.forEach { label ->
                DropdownMenuItem(
                    text = { Text(text = label.name, fontSize = fontSize) },
                    onClick = {
                        onLabelSelected(label)
                        isExpanded = false
                    }
                )
            }
        }
    }




}


@Preview
@Composable
private fun LabelDropdownPrev() {
    val labels = listOf(Label("test", Color.Yellow), Label("test2", Color.Green))
    PocketPalTheme {
        Box(modifier = Modifier.fillMaxSize().background(color =  Color.White)){
            LabelDropdown(
                labels = labels,
                onLabelSelected = {}
            )
        }
    }
}