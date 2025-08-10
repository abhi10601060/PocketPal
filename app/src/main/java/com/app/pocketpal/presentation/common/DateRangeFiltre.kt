package com.app.pocketpal.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@Composable
fun DateRangeSelector(
    modifier: Modifier = Modifier,
    startDate: LocalDate = LocalDate.now().minusDays(7),
    endDate: LocalDate = LocalDate.now(),
    onStartDateChange: (String) -> Unit = {},
    onEndDateChange: (String) -> Unit = {}
) {
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }

    val dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Start Date ", fontSize = 15.sp)

        Text(
            text = startDate.format(dateFormatter),
            modifier = Modifier
                .clickable { showStartDatePicker = true }
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 8.dp, vertical = 8.dp),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.bodySmall
        )

        // Separator
        Text(text = " - End Date ", fontSize = 15.sp)

        // End Date
        Text(
            text = endDate.format(dateFormatter),
            modifier = Modifier
                .clickable { showEndDatePicker = true }
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(5.dp)
                )
                .padding(horizontal = 12.dp, vertical = 8.dp),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.bodySmall
        )
    }

    if (showStartDatePicker) {
        DatePickerDialog(
            onDateSelected = { selectedDate ->
                onStartDateChange(selectedDate)
                showStartDatePicker = false
            },
            onDismiss = { showStartDatePicker = false },
            title = "Select Start Date"
        )
    }

    if (showEndDatePicker) {
        DatePickerDialog(
            onDateSelected = { selectedDate ->
                onEndDateChange(selectedDate)
                showEndDatePicker = false
            },
            onDismiss = { showEndDatePicker = false },
            title = "Select End Date",
            minDate = startDate.toString()
        )
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit,
    title: String,
    minDate: String = ""
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                datePickerState.selectedDateMillis?.let { millis ->
                    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val selectedDate = formatter.format(Date(millis))
                    onDateSelected(selectedDate)
                }
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}


// Usage Example
@Preview
@Composable
fun DateRangePrev() {
    DateRangeSelector()
}