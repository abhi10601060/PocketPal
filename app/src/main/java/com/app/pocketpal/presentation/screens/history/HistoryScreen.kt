package com.app.pocketpal.presentation.screens.history

import android.annotation.SuppressLint
import android.icu.text.DateFormat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import co.yml.charts.common.extensions.isNotNull
import com.app.pocketpal.constant.dateStringToMillis
import com.app.pocketpal.data.room.model.Expense
import com.app.pocketpal.presentation.common.DateRangeSelector
import com.app.pocketpal.presentation.screens.entry.EntryScreen
import com.app.pocketpal.presentation.ui.theme.PocketPalTheme
import com.app.pocketpal.presentation.ui.theme.ThemeColor
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HistoryScreen(
    isDarkTheme: Boolean = false,
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    var showEntryDialog by remember { mutableStateOf(false) }
    var viewExpense by remember { mutableStateOf<Expense?>(null) }
    val list by viewModel.listOfExpenses.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            HistoryFilter()

            LazyColumn (
                modifier = Modifier.fillMaxSize()
            ){
                items(items = list) {
                    ExpenseItem(modifier = Modifier.clickable{if(!showEntryDialog) viewExpense = it else viewExpense = null},expense = it)
                }
            }
        }


        if (!showEntryDialog) {
            FloatingActionButton(
                onClick = {
                    showEntryDialog = true
                },
                modifier = modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 30.dp, bottom = 30.dp),
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
    }

    if (showEntryDialog){
        EntryScreen(todayTotal = viewModel.todayAmount ,onCancelClicked = {showEntryDialog = false})
    }

    if (viewExpense.isNotNull()){
        EntryScreen(todayTotal = viewModel.todayAmount ,onCancelClicked = {viewExpense = null}, isViewModeOn = true, viewExpense = viewExpense)
    }
}

@Composable
fun HistoryFilter(modifier: Modifier = Modifier, onStartDateChanged: (Long) -> Unit = {}, onEndDateChanged: (Long) -> Unit = {}) {
    var startDate by remember { mutableStateOf(LocalDate.now().minusDays(7)) }
    var endDate by remember { mutableStateOf(LocalDate.now()) }

    val dateFormatter= DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
    Row(
        modifier= Modifier.fillMaxWidth()
    ) {
        DateRangeSelector(
            startDate = startDate,
            endDate =  endDate,
            onStartDateChange = { date->
                    onStartDateChanged(dateStringToMillis(date))
                    startDate = LocalDate.parse(date, dateFormatter)
                },
            onEndDateChange = { date->
                    onEndDateChanged(dateStringToMillis(date))
                    endDate = LocalDate.parse(date, dateFormatter)
                }
        )
    }
}

@Preview
@Composable
private fun HistoryScreenPrev() {
    PocketPalTheme {
        HistoryScreen(isDarkTheme = false)
    }
}