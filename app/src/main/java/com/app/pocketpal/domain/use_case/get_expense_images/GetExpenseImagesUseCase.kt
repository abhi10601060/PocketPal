package com.app.pocketpal.domain.use_case.get_expense_images

import android.content.Context
import android.graphics.BitmapFactory
import coil3.Bitmap
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class GetExpenseImagesUseCase @Inject constructor(val context: Context) {

    operator fun invoke(expenseId : String) = flow {
        val picturesDir = context.getExternalFilesDir(null)
        val expenseFolder = File(picturesDir, expenseId)
        val imagesList = getImagesFromDirectory(expenseFolder, true)
        emit(imagesList)
    }

    private fun getImagesFromDirectory(directory: File, recursive: Boolean = false) : List<Bitmap> {
        val imagesList = mutableListOf<Bitmap>()
        val imageExtensions = setOf("jpg")

        try {
            if (!directory.exists()) return imagesList
            directory.listFiles()?.forEach { file ->
                when {
                    file.isFile && imageExtensions.contains(file.extension.lowercase()) -> {
                        val bitmap = imagePathToBitmap(file.absolutePath)
                        bitmap?.let {
                            imagesList.add(it)
                        }
                    }
                    recursive && file.isDirectory -> {
                        imagesList.addAll(getImagesFromDirectory(file, recursive))
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return imagesList
    }

    fun imagePathToBitmap(imagePath: String): Bitmap? {
        return try {
            val file = File(imagePath)
            if (file.exists()) {
                BitmapFactory.decodeFile(imagePath)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}