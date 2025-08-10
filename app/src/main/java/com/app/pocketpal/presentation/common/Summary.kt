package com.app.pocketpal.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.pocketpal.constant.getColorForLable
import com.app.pocketpal.domain.model.ExpensePieChartData
import java.text.NumberFormat
import java.util.*

// Data classes
data class SpendingLegend(
    val label: String,
    val amount: Double,
    val color: Color
)

@Composable
fun SpendingLegend(
    expenses: List<ExpensePieChartData>,
    modifier: Modifier = Modifier,
    showTotal: Boolean = true
) {
    val spendingByLabel = remember(expenses) {
        expenses.groupBy { it.label }
            .map { (label, expenseList) ->
                SpendingLegend(
                    label = label,
                    amount = expenseList.sumOf { it.total.toDouble() },
                    color = getColorForLable(label)
                )
            }
            .sortedByDescending { it.amount }
    }

    val totalSpending = remember(expenses) {
        expenses.sumOf { it.total }
    }

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        if (showTotal) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF8F9FA)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total Spending",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "₹$totalSpending",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2563EB)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }

        // Legend items
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            spendingByLabel.forEach { legend ->
                LegendItem(
                    legend = legend,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun LegendItem(
    legend: SpendingLegend,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Color indicator
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(legend.color)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Label
            Text(
                text = legend.label,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }

        // Amount
        Text(
            text = "₹${legend.amount}",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF374151)
        )
    }
}

// Compact version - single row
@Composable
fun CompactSpendingLegend(
    expenses: List<ExpensePieChartData>,
    modifier: Modifier = Modifier
) {
    val spendingByLabel = remember(expenses) {
        expenses.groupBy { it.label }
            .map { (label, expenseList) ->
                SpendingLegend(
                    label = label,
                    amount = expenseList.sumOf { it.total.toDouble() },
                    color = getColorForLable(label)
                )
            }
    }

    val totalSpending = expenses.sumOf { it.total }

    Column(modifier = modifier) {
        // Total
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFF8F9FA),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Total", fontWeight = FontWeight.Bold)
            Text(
                totalSpending.toString(),
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2563EB)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Categories
        spendingByLabel.forEach { legend ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(legend.color)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = legend.label,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Text(
                    text = legend.amount.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

// Usage example
@Preview
@Composable
fun ExampleUsage() {
    val sampleExpenses = listOf(
        ExpensePieChartData("Food", 20.0f, Color.Red, 234),
        ExpensePieChartData("Travel", 30.0f, Color.Blue, 234),
    )

    Column {
        // Full version
        SpendingLegend(
            expenses = sampleExpenses,
            showTotal = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Compact version
        CompactSpendingLegend(
            expenses = sampleExpenses
        )
    }
}