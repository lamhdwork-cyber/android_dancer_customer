package com.hdl.dancer.booking.app

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.hdl.dancer.booking.data.helper.file.FileUtils
import androidx.core.net.toUri

class AppSettings(private val context: Context) {

    companion object {
        const val CHAT_GALLERY_MAX_IMAGES = 6
    }

    fun openGalleryForImage(launcher: ActivityResultLauncher<PickVisualMediaRequest>) {
        launcher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }

    fun openGalleryForImages(launcher: ActivityResultLauncher<PickVisualMediaRequest>) {
        openGalleryForImage(launcher)
    }

    fun openCameraForImage(
        rsCaptureLauncher: ActivityResultLauncher<Intent>,
        onResultListener: (Uri?) -> Unit = {}
    ) {
        val imageURI: Uri? = if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            FileUtils.createImageFileMediaStore(context)
        } else {
            context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                ContentValues().apply {
                    put(MediaStore.Images.Media.TITLE, "New Picture")
                    put(MediaStore.Images.Media.DESCRIPTION, "From your Camera")
                })
        }
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, imageURI)
        }
        onResultListener(imageURI)
        rsCaptureLauncher.launch(cameraIntent)
    }

    fun sendEmail(
        email: String,
        subject: String? = "",
        message: String? = ""
    ) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = "mailto:".toUri()
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, message)
        context.startActivity(intent)
    }

    fun call(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = "tel:$phoneNumber".toUri()
        context.startActivity(intent)
    }
}