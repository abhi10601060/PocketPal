package com.app.pocketpal.presentation.screens.entry

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import co.yml.charts.common.extensions.isNotNull
import com.app.pocketpal.constant.LABEL_LIST
import com.app.pocketpal.constant.rotate
import com.app.pocketpal.constant.uriToBitmap
import com.app.pocketpal.data.room.model.Expense
import com.app.pocketpal.presentation.common.AmountTextField
import com.app.pocketpal.presentation.common.LabelDropdown
import com.app.pocketpal.presentation.common.PocketPalTextField
import com.app.pocketpal.presentation.ui.theme.LightAccent
import com.app.pocketpal.presentation.ui.theme.PocketPalTheme

@Composable
fun EntryScreen(modifier: Modifier = Modifier,
                viewModel: EntryScreenViewModel= hiltViewModel(key = "" + System.currentTimeMillis()),
                todayTotal : Int, onCancelClicked: () -> Unit,
                isViewModeOn : Boolean = false,
                viewExpense : Expense? = null
) {

    LaunchedEffect(key1 = true) {
        if (isViewModeOn){
            viewExpense?.let {
                viewModel.loadDataForExpense(expense = it)
            }
        }
    }

    Dialog(
        onDismissRequest = { /* Handle dismiss request */ },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        ),
    ){

        if (viewModel.entryScreenState.success){
            Toast.makeText(LocalContext.current, "Expense entry created successfully...", Toast.LENGTH_SHORT).show()
            onCancelClicked()
        }

        if (viewModel.entryScreenState.error.isNotEmpty()){
            ErrorDialog(viewModel.entryScreenState.error) {
                viewModel.entryScreenState = EntryScreenState()
            }
        }

        Column (modifier
            .padding(horizontal = 20.dp, vertical = 30.dp)
            .fillMaxSize()
            .clip(RoundedCornerShape(15.dp))
            .background(color = MaterialTheme.colorScheme.background)
            .padding(start = 10.dp, end = 10.dp)
        ){
            Row(modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = if(isViewModeOn) "Expense Details" else "Today's Total - ₹${todayTotal}", fontSize = 19.sp, fontWeight = FontWeight.Bold)
                EntryScreenOptions(onCancelClicked = onCancelClicked)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Title", fontSize = 19.sp, fontWeight = FontWeight.Bold)
            PocketPalTextField( modifier
                .padding(top = 5.dp)
                .fillMaxWidth(),
                readOnly = isViewModeOn,
                value = viewModel.title,
                singleLine = true,
                onValueChange = {viewModel.title = it.trim()})

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Description", fontSize = 19.sp, fontWeight = FontWeight.Bold)
            PocketPalTextField( modifier
                .padding(top = 5.dp)
                .fillMaxWidth(),
                readOnly = isViewModeOn,
                value = viewModel.description,
                onValueChange = {viewModel.description = it},
                singleLine = false, minLines = 5)

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Column(
                    modifier = Modifier.weight(0.4f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text =if (isViewModeOn) "Label" else "Add Label", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    LabelDropdown(modifier = Modifier.padding(top = 5.dp), labels = LABEL_LIST, value = viewModel.selectedLabel, readOnly = isViewModeOn) { label->  viewModel.selectedLabel = label}
                }

                Column(
                    modifier = Modifier.weight(0.6f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = if (isViewModeOn) "Amount" else "Enter Amount", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    AmountTextField(value = viewModel.amount.toString(), onValueChange = {viewModel.amount = if (it.isEmpty()) 0 else it.trim().toInt() }, fontSize = 18.sp, readOnly = isViewModeOn)
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            ImageCaptureOptions(isViewModeOn = isViewModeOn){ bitmap ->
                viewModel.images.add(bitmap)
            }

            LazyRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                itemsIndexed (viewModel.images.toList()) { _, bm ->
                    ImageCardWithCancel(bitmap = bm, isViewModeOn= isViewModeOn, onRemove = {viewModel.images.remove(bm)})
                }

            }

        }

        if (!isViewModeOn){
            Box(modifier = Modifier.fillMaxSize()){
                Button(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 40.dp),
                    onClick = { viewModel.saveData() }
                ) {
                    Text(text = "Save")
                }
            }
        }
    }
}


@Composable
fun EntryScreenOptions(modifier: Modifier = Modifier, onCancelClicked: () -> Unit = {}, onEditClicked: () -> Unit = {}){
    Row(modifier = modifier,
    ) {
        Icon(modifier = modifier
            .alpha(0.8f)
            .clickable { onCancelClicked() }, imageVector = Icons.Default.Cancel, contentDescription = "close", tint = Color.Red)
    }
}

@Composable
fun ErrorDialog(
    message: String,
    onDismiss: () -> Unit
){
    AlertDialog(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(color = LightAccent),
        onDismissRequest = onDismiss,
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Input Required",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("OK")
            }
        }
    )

}

@Composable
fun ImageCaptureOptions(modifier: Modifier = Modifier, isViewModeOn: Boolean = false, onImageAdded : (Bitmap) -> Unit ) {
    val context = LocalContext.current

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            onImageAdded(it.rotate(90f))
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        val bitmap = uriToBitmap(context, uri!!)
        if (bitmap.isNotNull()) {
            onImageAdded(bitmap!!)
        } else {
            Toast.makeText(context, "Unable to upload...", Toast.LENGTH_SHORT).show()
        }

    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraLauncher.launch(null)
        } else {
            Toast.makeText(context, "Camera permission required", Toast.LENGTH_SHORT).show()
        }
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Add Receipt", fontSize = 19.sp, fontWeight = FontWeight.Bold)

        if(!isViewModeOn){
            Row {
                Icon(modifier = Modifier.clickable{
                    val permission = Manifest.permission.CAMERA
                    if (ContextCompat.checkSelfPermission(context, permission)
                        == PackageManager.PERMISSION_GRANTED) {
                        cameraLauncher.launch(null)
                    } else {
                        cameraPermissionLauncher.launch(permission)
                    }
                },
                    imageVector = Icons.Default.PhotoCamera,
                    contentDescription = "camera"
                )

                Spacer(modifier = Modifier.width(20.dp))

                Icon(modifier = Modifier.clickable{
                    galleryLauncher.launch("image/*")
                },
                    imageVector = Icons.Default.Upload,
                    contentDescription = "upload"
                )
            }
        }
    }
}

@Composable
fun ImageCardWithCancel(
    bitmap: Bitmap,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
    isViewModeOn : Boolean = false
) {
    Surface ( modifier = modifier.padding(10.dp), shadowElevation = 10.dp ) {
        Box(
            modifier = Modifier.padding(horizontal = 5.dp).size(width = 150.dp, 220.dp),
        ) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Selected Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            if (!isViewModeOn){
                Icon(
                    modifier = Modifier.size(30.dp).offset(y = 0.dp).align(Alignment.BottomCenter).clickable{onRemove()},
                    imageVector = Icons.Outlined.Cancel,
                    contentDescription = "Remove Image",
                    tint = MaterialTheme.colorScheme.error,
                )
            }
        }
    }
}


@Preview
@Composable
private fun EntryScreenPrev() {
    PocketPalTheme {
        EntryScreen(todayTotal = 100,onCancelClicked = {})
//        ErrorDialog("dada") { }
    }
}