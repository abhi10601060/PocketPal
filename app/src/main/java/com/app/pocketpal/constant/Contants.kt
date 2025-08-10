package com.app.pocketpal.constant

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.compose.ui.graphics.Color
import com.app.pocketpal.domain.model.Label
import com.app.pocketpal.presentation.ui.theme.GreenSurface
import com.app.pocketpal.presentation.ui.theme.YellowSurface
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

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


fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // For Android P (API 28) and above
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            // For older Android versions
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
        null
    } catch (e: SecurityException) {
        e.printStackTrace()
        null
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}