package com.app.pocketpal.domain.use_case.save_images

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject
import kotlin.text.insert

class SaveImagesUseCase @Inject constructor(val context: Context) {

    operator fun invoke(list: List<Bitmap>, expenseId : String) = flow {
        list.forEachIndexed { idx, bitmap ->
            withContext(Dispatchers.IO) {
                val ans = saveBitmapToExternalStorage(bitmap, expenseId, "image_${idx}")
                Log.d("IMAGE", "invoke: image ssaved  : " + ans)
            }
        }
        emit(true)
    }

    private fun saveBitmapToExternalStorage(bitmap: Bitmap, folderName : String, fileName: String): Boolean {
        return try {
            val picturesDir = context.getExternalFilesDir(null)
            val expenseFolder = File(picturesDir, folderName)
            if (!expenseFolder.exists()){
                expenseFolder.mkdirs()
            }
            val file = File(expenseFolder, "$fileName.jpg")

            FileOutputStream(file).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 95, outputStream)
            }

            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        } catch (e: SecurityException) {
            e.printStackTrace()
            false
        }
    }
}

