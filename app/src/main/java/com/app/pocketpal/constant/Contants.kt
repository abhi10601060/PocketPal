package com.app.pocketpal.constant

import androidx.compose.ui.graphics.Color
import com.app.pocketpal.domain.model.Label
import com.app.pocketpal.presentation.ui.theme.GreenSurface
import com.app.pocketpal.presentation.ui.theme.YellowSurface

val MAIN_SCREEN_TABS = listOf("Dashboard", "History")


val LABEL_LIST = listOf(
    Label("Staff", YellowSurface),
    Label("Utility", GreenSurface),
    Label("Food", Color.Red),
    Label("Travel", Color.Blue),
)

fun getColorForLable(name: String) : Color {
    return when(name){
        "Staff" -> YellowSurface
        "Utility" -> GreenSurface
        "Food" -> Color.Red
        "Travel" -> Color.Blue
        else -> Color.Magenta
    }
}


fun createPastelColor(color: Color, lightness: Float = 0.8f): Color {
    return Color(
        red = color.red + (1f - color.red) * lightness,
        green = color.green + (1f - color.green) * lightness,
        blue = color.blue + (1f - color.blue) * lightness,
        alpha = 1f
    )
}