package io.github.tuguzt.pcbuilder.presentation.view

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.net.toUri
import io.github.tuguzt.pcbuilder.R
import java.io.File
import java.io.File.separator
import java.io.FileOutputStream
import java.io.OutputStream

/**
 * Saves image to the user gallery.
 */
fun saveImage(bitmap: Bitmap, context: Context): Uri? {
    val folderName = context.resources.getString(R.string.app_name)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val values = contentValues()
        values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/$folderName")
        values.put(MediaStore.Images.Media.IS_PENDING, true)

        val uri = context.contentResolver
            .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values) ?: return null
        saveImageToStream(bitmap, context.contentResolver.openOutputStream(uri))
        values.put(MediaStore.Images.Media.IS_PENDING, false)
        context.contentResolver.update(uri, values, null, null)
        return uri
    }

    val directory =
        File(Environment.getExternalStorageDirectory().toString() + separator + folderName)
    if (!directory.exists()) {
        directory.mkdirs()
    }

    val fileName = "${System.currentTimeMillis()}.png"
    val file = File(directory, fileName)
    saveImageToStream(bitmap, FileOutputStream(file))

    val values = contentValues()
    values.put(MediaStore.Images.Media.DATA, file.absolutePath)
    context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

    return file.toUri()
}

private fun contentValues(): ContentValues {
    val values = ContentValues()
    values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
    values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
    values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
    return values
}

private fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?) {
    if (outputStream != null) {
        try {
            outputStream.use {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            }
        } catch (exception: Exception) {
            Log.e(::saveImageToStream.name, "Failed to save an image", exception)
        }
    }
}
