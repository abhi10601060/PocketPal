package com.app.pocketpal.presentation.screens.dashboard

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import co.yml.charts.common.components.Legends
import co.yml.charts.common.model.PlotType
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.app.pocketpal.domain.model.toPieChartSlice
import com.app.pocketpal.presentation.common.SpendingLegend
import com.app.pocketpal.presentation.ui.theme.PocketPalTheme

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DashboardScreen(
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier,
    viewModel: DashBoardViewModel = hiltViewModel()
) {

    val expensePieChartData by viewModel.pieChartData.collectAsState()
    val allExpenses by viewModel.listOfExpenses.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            if (allExpenses.isNotEmpty()){
                val pieChartData = mutableListOf<PieChartData.Slice>()
                expensePieChartData.forEach {
                    pieChartData.add(it.toPieChartSlice())
                }
                Text("Last 7 Days Overview", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                SimpleDonutChart(pieChartData)
                SpendingLegend(expenses = expensePieChartData)
            }else{
                Text(text = "No Data Found",
                    modifier = Modifier,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.error)
            }
        }
    }
}


@Composable
private fun SimpleDonutChart(slices: List<PieChartData.Slice>) {
    val context = LocalContext.current
    val data = PieChartData(
            slices = slices,
            plotType = PlotType.Donut
        )

    val pieChartConfig =
        PieChartConfig(
            labelVisible = true,
            strokeWidth = 120f,
            labelColor = Color.Black,
            activeSliceAlpha = .9f,
            isEllipsizeEnabled = true,
            labelTypeface = Typeface.defaultFromStyle(Typeface.BOLD),
            isAnimationEnable = true,
            chartPadding = 25,
            labelFontSize = 42.sp,
        )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(420.dp)
    ) {
        Legends(legendsConfig = DataUtils.getLegendsConfigFromPieChartData(pieChartData = data, 3))
        DonutPieChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            data,
            pieChartConfig
        ) { slice -> }
    }
}


@Preview
@Composable
private fun DashBoardScreenPrev() {
    PocketPalTheme {
        DashboardScreen(isDarkTheme = true)
    }
}