package com.app.pocketpal.presentation.screens.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.pocketpal.presentation.ui.theme.PocketPalTheme

@Composable
fun DashboardScreen(
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier
) {
    val contentColor = if (isDarkTheme) Color.White else Color.Black

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Dashboard",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = contentColor,
            textAlign = TextAlign.Center
        )
    }
}


@Preview
@Composable
private fun DashBoardScreenPrev() {
    PocketPalTheme {
        DashboardScreen(isDarkTheme = true)
    }
}