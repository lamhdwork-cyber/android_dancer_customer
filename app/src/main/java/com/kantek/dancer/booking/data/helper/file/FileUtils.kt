package com.kantek.dancer.booking.data.helper.file

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.annotation.RequiresApi
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID

object FileUtils {
    private const val PHOTO_FILE_NAME = "img_"
    private const val PHOTO_FOLDER_NAME_Q = "Pictures/Lawyer"
    private const val PHOTO_CHAT = "Lawyer/Conversation"

    @RequiresApi(api = Build.VERSION_CODES.Q)
    fun createImageFileMediaStore(cxt: Context): Uri? {
        @SuppressLint("SimpleDateFormat") val timeStamp =
            SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val values = ContentValues(4)
        values.put(MediaStore.Audio.Media.DISPLAY_NAME, PHOTO_FILE_NAME + timeStamp)
        values.put(MediaStore.Audio.Media.DATE_ADDED, (System.currentTimeMillis() / 1000).toInt())
        values.put(MediaStore.Audio.Media.MIME_TYPE, "image/*")
        values.put(MediaStore.Audio.Media.RELATIVE_PATH, PHOTO_FOLDER_NAME_Q)
        return cxt.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    }

    fun saveImage(bitmap: Bitmap): File {
        val file = File(getPathFolderImageConversation(), getImageFileNam())
        FileOutputStream(file).use { fos ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fos)
        }
        return file
    }

    private fun getPathFolderImageConversation(): String {
        val folder =
            File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                PHOTO_CHAT
            )
        if (!folder.exists()) {
            if (!folder.mkdirs()) throw RuntimeException("Failed to create directory photo")
        }
        return folder.absolutePath
    }

    private fun getImageFileNam(): String {
        @SuppressLint("SimpleDateFormat") val timeStamp =
            SimpleDateFormat("yyyyMMdd_HHmmss").format(
                Date()
            )
        return "/top_services_$timeStamp${UUID.randomUUID()}.jpg"
    }

    /**
     * Get mime type of file
     *
     * @param url
     * @return Type of file Ex: For image: Png, JPG...
     */
    fun getMimeType(url: String): String? {
        var type: String? = null
        var extension = ""
        val i: Int = url.lastIndexOf('.')
        if (i > 0) {
            extension = url.substring(i + 1)
        }
        type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        return type
    }

    /**
     * Method for return file path of Gallery image/ Document / Video / Audio
     *
     * @param context
     * @param uri
     * @return path of the selected image file from gallery
     */
    @SuppressLint("NewApi")
    fun getPath(context: Context, uri: Uri): String? {
        if (DocumentsContract.isDocumentUri(context, uri)) {
            when {
                isExternalStorageDocument(uri) -> return getExternalPath(context, uri)
                isDownloadsDocument(uri) -> return getPathInDownloads(context, uri)
                isMediaDocument(uri) -> return getPathInDocuments(context, uri)
            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            return getContentPath(context, uri)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return uri.path
    }

    private fun getPathInDocuments(context: Context, uri: Uri): String? {
        val docId = DocumentsContract.getDocumentId(uri)
        val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val type = split[0]

        var contentUri: Uri? = null
        when (type) {
            "image" -> contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            "video" -> contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            "audio" -> contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        }
        val selection = "_id=?"
        val selectionArgs = arrayOf(split[1])

        return getDataColumn(
            context, contentUri, selection,
            selectionArgs
        )
    }

    private fun getPathInDownloads(context: Context, uri: Uri): String? {
        val id = DocumentsContract.getDocumentId(uri)
        val contentUri = ContentUris.withAppendedId(
            Uri.parse("content://downloads/public_downloads"),
            java.lang.Long.valueOf(id)
        )

        return getDataColumn(context, contentUri, null, null)
    }

    private fun getExternalPath(context: Context, uri: Uri): String? {
        val docId = DocumentsContract.getDocumentId(uri)
        val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val type = split[0]

        if ("primary".equals(type, ignoreCase = true)) {
            return "${context.getExternalFilesDir("/")}${split[1]}"
        }
        return null
    }

    private fun getContentPath(context: Context, uri: Uri): String? {
        return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(
            context,
            uri,
            null,
            null
        )
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context
     * The context.
     * @param uri
     * The Uri to query.
     * @param selection
     * (Optional) Filter used in the query.
     * @param selectionArgs
     * (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    @JvmStatic
    fun getDataColumn(
        context: Context, uri: Uri?,
        selection: String?, selectionArgs: Array<String>?
    ): String? {
        if (uri == null) return null
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)
        val path: String?

        try {
            cursor = context.contentResolver.query(uri, projection, selection, selectionArgs, null)
            if (cursor == null) return null
            if (!cursor.moveToFirst()) return null
            val index = cursor.getColumnIndexOrThrow(column)
            path = cursor.getString(index)
        } finally {
            cursor?.close()
        }
        return path
    }

    /**
     * @param uri
     * The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    @JvmStatic
    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri
            .authority
    }

    /**
     * @param uri
     * The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    @JvmStatic
    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri
            .authority
    }

    /**
     * @param uri
     * The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    @JvmStatic
    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri
            .authority
    }

    /**
     * @param uri
     * The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    @JvmStatic
    fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri
            .authority
    }
}