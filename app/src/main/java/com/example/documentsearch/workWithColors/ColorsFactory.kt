package com.example.documentsearch.workWithColors

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap

class ColorsFactory {
    fun changeColor(imageBitmap: ImageBitmap, targetColor: Int, replacementColor: Int): ImageBitmap {
        val originalBitmap = imageBitmap.asAndroidBitmap()

        val width = originalBitmap.width
        val height = originalBitmap.height
        val resultBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        for (y in 0 until height) {
            for (x in 0 until width) {
                val pixel = originalBitmap.getPixel(x, y)

                // Проверяем цвет пикселя, и если он соответствует целевому цвету, меняем его на цвет замены.
                if (pixel == targetColor)
                    resultBitmap.setPixel(x, y, replacementColor)
                else
                    resultBitmap.setPixel(x, y, pixel)
            }
        }

        return resultBitmap.asImageBitmap()
    }
}

