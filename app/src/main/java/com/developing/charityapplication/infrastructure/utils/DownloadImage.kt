package com.developing.charityapplication.infrastructure.utils

import android.content.Context
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

object DownloadImage {
    fun downloadImageToFile(context: Context, imageUrl: String): File? {
        return try {
            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()

            val extension = when {
                imageUrl.endsWith(".png", ignoreCase = true) -> ".png"
                imageUrl.endsWith(".jpg", ignoreCase = true) || imageUrl.endsWith(".jpeg", ignoreCase = true) -> ".jpg"
                else -> ".jpg" // fallback nếu không rõ
            }

            val inputStream = BufferedInputStream(connection.inputStream)
            val file = File(context.cacheDir, "downloaded_${System.currentTimeMillis()}$extension")
            val outputStream = FileOutputStream(file)

            val buffer = ByteArray(1024)
            var count: Int
            while (inputStream.read(buffer).also { count = it } != -1) {
                outputStream.write(buffer, 0, count)
            }

            outputStream.flush()
            outputStream.close()
            inputStream.close()

            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    fun createImagePartFromFile(file: File): MultipartBody.Part {
        val mimeType = when {
            file.extension.equals("png", ignoreCase = true) -> "image/png"
            else -> "image/jpeg"
        }

        val requestFile = file.asRequestBody(mimeType.toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("avatar", file.name, requestFile)
    }
}