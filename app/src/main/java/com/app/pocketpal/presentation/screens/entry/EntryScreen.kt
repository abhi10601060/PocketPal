package com.app.pocketpal.presentation.screens.entry

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.app.pocketpal.constant.LABEL_LIST
import com.app.pocketpal.domain.model.Label
import com.app.pocketpal.presentation.common.AmountTextField
import com.app.pocketpal.presentation.common.LabelDropdown
import com.app.pocketpal.presentation.common.PocketPalTextField
import com.app.pocketpal.presentation.ui.theme.PocketPalTheme

@Composable
fun EntryScreen(modifier: Modifier = Modifier, onCancelClicked: () -> Unit) {

    var selectedLabel by remember { mutableStateOf<Label?>(null) }

    Dialog(
        onDismissRequest = { /* Handle dismiss request */ },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        ),
    ){
        Column (modifier
            .padding(horizontal = 20.dp, vertical = 30.dp)
            .fillMaxSize()
            .clip(RoundedCornerShape(20.dp))
            .background(color = MaterialTheme.colorScheme.background)
            .padding(start = 10.dp, end = 10.dp)
        ){
            Row(modifier = modifier.fillMaxWidth()
                .padding(top = 8.dp, start = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = "Today's Total - ₹1000", fontSize = 19.sp, fontWeight = FontWeight.Bold)
                EntryScreenOptions(onCancelClicked = onCancelClicked)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Title", fontSize = 19.sp, fontWeight = FontWeight.Bold)
            PocketPalTextField( modifier.padding(top = 5.dp).fillMaxWidth(), value = "asadad", singleLine = true)

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Description", fontSize = 19.sp, fontWeight = FontWeight.Bold)
            PocketPalTextField( modifier.padding(top = 5.dp).fillMaxWidth(), value = "asadad", singleLine = false, minLines = 5)

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Column(
                    modifier = Modifier.weight(0.4f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Add Label", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    LabelDropdown(modifier = Modifier.padding(top = 5.dp), labels = LABEL_LIST, value = selectedLabel) { label->  selectedLabel = label}
                }

                Column(
                    modifier = Modifier.weight(0.6f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Enter Amount", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    AmountTextField(value = "1000000", fontSize = 18.sp)
                }
            }

        }

        Box(modifier = Modifier.fillMaxSize()){
            Button(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 40.dp),
                onClick = {}
            ) {
                Text(text = "Save")
            }
        }
    }
}


@Composable
fun EntryScreenOptions(modifier: Modifier = Modifier, onCancelClicked: () -> Unit = {}, onEditClicked: () -> Unit = {}){
    Row(modifier = modifier,
    ) {
        Icon(modifier = modifier.alpha(0.8f).clickable{onCancelClicked()}, imageVector = Icons.Default.Cancel, contentDescription = "close", tint = Color.Red)
    }
}


@Preview
@Composable
private fun EntryScreenPrev() {
    PocketPalTheme {
        EntryScreen(onCancelClicked = {})
    }
}