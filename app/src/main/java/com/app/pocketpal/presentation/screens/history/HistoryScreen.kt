package com.app.pocketpal.presentation.screens.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.pocketpal.presentation.screens.entry.EntryScreen
import com.app.pocketpal.presentation.ui.theme.PocketPalTheme
import com.app.pocketpal.presentation.ui.theme.ThemeColor

@Composable
fun HistoryScreen(
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier
) {
    val contentColor = if (isDarkTheme) Color.White else Color.Black

    var showEntryDialog by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "History",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = contentColor,
            textAlign = TextAlign.Center
        )

        if (!showEntryDialog) {
            FloatingActionButton(
                onClick = {
                    showEntryDialog = true
                },
                modifier = modifier.align(Alignment.BottomEnd).padding(end = 30.dp, bottom = 30.dp),
                containerColor = ThemeColor,
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 12.dp,
                    pressedElevation = 16.dp,
                    focusedElevation = 14.dp,
                    hoveredElevation = 14.dp
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Transaction",
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        if (showEntryDialog){

            EntryScreen(onCancelClicked = {showEntryDialog = false})
        }
    }
}

@Preview
@Composable
private fun HistoryScreenPrev() {
    PocketPalTheme {
        HistoryScreen(isDarkTheme = false)
    }
}