package com.developing.charityapplication.infrastructure.utils

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

object DownloadImage {
    suspend fun prepareImageParts(context: Context, fileId: String, tag: String): MultipartBody.Part {
        val file = downloadImageToFile(context, fileId)
            ?: throw IllegalStateException("Failed to download image: $fileId").also {
                Log.e("EditContent", "Failed to download image: $fileId")
            }

        val mediaPart = createImagePartFromFile(file, tag)
        Log.d("EditContent", "MediaPart created: ${file.name}")
        return mediaPart
    }

    suspend fun downloadImageToFile(context: Context, imageUrl: String): File? = withContext(Dispatchers.IO) {
        try {
            Log.d("DownloadImage", "Attempting to download: $imageUrl")
            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = 10000 // 10 giây timeout
            connection.readTimeout = 10000
            connection.setRequestProperty("User-Agent", "Mozilla/5.0") // Thêm User-Agent
            connection.connect()

            val responseCode = connection.responseCode
            if (responseCode != HttpURLConnection.HTTP_OK) {
                Log.e("DownloadImage", "HTTP error code: $responseCode for $imageUrl")
                connection.disconnect()
                return@withContext null
            }

            val extension = when {
                imageUrl.endsWith(".png", ignoreCase = true) -> ".png"
                imageUrl.endsWith(".jpg", ignoreCase = true) || imageUrl.endsWith(".jpeg", ignoreCase = true) -> ".jpg"
                else -> ".jpg"
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
            connection.disconnect()

            Log.d("DownloadImage", "Successfully downloaded: ${file.absolutePath}")
            file
        } catch (e: Exception) {
            Log.e("DownloadImage", "Error downloading $imageUrl: ${e.javaClass.simpleName} - ${e.message}")
            null
        }
    }

    suspend fun createImagePartFromFile(file: File, tag: String): MultipartBody.Part {
        val mimeType = when {
            file.extension.equals("png", ignoreCase = true) -> "image/png"
            file.extension.equals("jpg", ignoreCase = true) -> "image/jpg"
            else -> "image/jpeg"
        }

        val requestFile = file.asRequestBody(mimeType.toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(tag, file.name, requestFile)
    }
}