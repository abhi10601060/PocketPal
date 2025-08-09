package com.app.pocketpal.presentation.screens.main_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.pocketpal.constant.MAIN_SCREEN_TABS
import com.app.pocketpal.presentation.screens.dashboard.DashboardScreen
import com.app.pocketpal.presentation.screens.history.HistoryScreen
import com.app.pocketpal.presentation.ui.theme.PocketPalTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    isDarkTheme: Boolean = false,
    modifier: Modifier = Modifier
) {
    val primaryColor = Color(0xFF5375F6)
    val backgroundColor = if (isDarkTheme) Color(0xFF1A1A1A) else Color(0xFFFFFFFF)
    val contentColor = if (isDarkTheme) Color.White else Color.Black
    val surfaceColor = if (isDarkTheme) Color(0xFF2D2D2D) else Color(0xFFF8FAFF)

    val tabs = MAIN_SCREEN_TABS
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = Modifier.fillMaxWidth(),
            containerColor = surfaceColor,
            contentColor = primaryColor,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    height = 0.dp,
                    color = primaryColor
                )
            },
            divider = {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = if (isDarkTheme) Color(0xFF404040) else Color(0xFFE0ECFF)
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = {
                        Text(
                            text = title,
                            fontSize = 18.sp,
                            fontWeight = if (pagerState.currentPage == index) FontWeight.Bold else FontWeight.Medium,
                            color = if (pagerState.currentPage == index) primaryColor else contentColor.copy(alpha = 0.6f)
                        )
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) { page ->
            when (page) {
                0 -> DashboardScreen(isDarkTheme = isDarkTheme)
                1 -> HistoryScreen(isDarkTheme = isDarkTheme)
            }
        }
    }
}





@Preview
@Composable
private fun MainScreenPrev() {
    PocketPalTheme(darkTheme = true) {
        MainScreen(isDarkTheme = false)
    }
}