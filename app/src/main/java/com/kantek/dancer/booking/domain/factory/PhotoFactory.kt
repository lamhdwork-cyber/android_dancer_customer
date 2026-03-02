package com.kantek.dancer.booking.domain.factory

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.support.core.extensions.safe
import androidx.exifinterface.media.ExifInterface
import com.kantek.dancer.booking.data.helper.file.FileUtils
import com.kantek.dancer.booking.domain.model.response.conversation.ImageDTO
import com.kantek.dancer.booking.domain.model.ui.media.IPhoto

class PhotoFactory(private val context: Context) {

    @SuppressLint("Recycle")
    fun isImageLargerThanMB(uri: Uri): Boolean {
        val fileSize = context.contentResolver.openAssetFileDescriptor(uri, "r")?.length ?: -1
        val fileSizeInMB = fileSize / (1024.0) // Convert to MB
        return fileSizeInMB > 1
    }

    private fun resizeImageFromUri(
        uri: Uri,
        maxWidth: Int = 1080,
        maxHeight: Int = 1920
    ): Bitmap? {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true

        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            BitmapFactory.decodeStream(inputStream, null, options)
        }

        var scaleFactor = 1
        if (options.outWidth > maxWidth || options.outHeight > maxHeight) {
            val widthScale = options.outWidth / maxWidth
            val heightScale = options.outHeight / maxHeight
            scaleFactor = maxOf(widthScale, heightScale)
        }

        options.inJustDecodeBounds = false
        options.inSampleSize = scaleFactor

        val bitmap = context.contentResolver.openInputStream(uri)?.use { inputStream ->
            BitmapFactory.decodeStream(inputStream, null, options)
        } ?: return null

        val orientation = getExifOrientation(context, uri)

        return rotateBitmap(bitmap, orientation)
    }

    private fun getExifOrientation(context: Context, uri: Uri): Int {
        val inputStream =
            context.contentResolver.openInputStream(uri) ?: return ExifInterface.ORIENTATION_NORMAL
        val exif = ExifInterface(inputStream)
        return exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
    }

    private fun rotateBitmap(bitmap: Bitmap, orientation: Int): Bitmap {
        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.preScale(-1f, 1f)
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> matrix.preScale(1f, -1f)
        }

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    fun resizeIfNeed(photos: List<Uri>?): List<String?>? {
        return photos?.map {
            if (isImageLargerThanMB(it)) {
                val bitmapResize = resizeImageFromUri(it)
                if (bitmapResize == null) FileUtils.getPath(context, it)
                else FileUtils.saveImage(bitmapResize).absolutePath
            } else FileUtils.getPath(context, it)
        }
    }

    fun resizeIfNeed(it: Uri): String? {
        return if (isImageLargerThanMB(it)) {
            val bitmapResize = resizeImageFromUri(it)
            if (bitmapResize == null) FileUtils.getPath(context, it)
            else FileUtils.saveImage(bitmapResize).absolutePath
        } else FileUtils.getPath(context, it)
    }

    fun createPhotos4Conversation(its: List<ImageDTO>?): List<IPhoto> {
        return its?.map(::createPhoto4Conversation) ?: listOf()
    }

    private fun createPhoto4Conversation(it: ImageDTO?): IPhoto {
        return object : IPhoto {
            override val id: Int
                get() = it?.id.safe()
            override val url: String
                get() = it?.source_url.safe()
        }
    }
}