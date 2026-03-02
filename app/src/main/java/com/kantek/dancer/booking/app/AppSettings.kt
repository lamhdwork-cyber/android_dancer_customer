package com.kantek.dancer.booking.app

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.ui.graphics.toArgb
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.data.helper.file.FileUtils
import com.kantek.dancer.booking.presentation.theme.Colors
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter

class AppSettings(private val context: Context) {

    fun openGalleryForImage(photoGalleryResults: ActivityResultLauncher<Intent>) {
        val activity =
            context as? ComponentActivity ?: error("PermissionProvider only support for Activity!")
        FishBun.with(activity)
            .setImageAdapter(GlideAdapter())
            .setMaxCount(1)
            .setActionBarColor(
                Colors.Primary.toArgb(),
                Colors.Primary.toArgb(),
                false
            )
            .setActionBarTitleColor(Color.WHITE)
            .startAlbumWithActivityResultCallback(photoGalleryResults)
    }

    fun openGalleryForImagesChat(photoGalleryResults: ActivityResultLauncher<Intent>) {
        val activity =
            context as? ComponentActivity ?: error("PermissionProvider only support for Activity!")
        FishBun.with(activity)
            .setImageAdapter(GlideAdapter())
            .setMaxCount(6)
            .setSelectedImages(arrayListOf())
            .setActionBarColor(
                Colors.Primary.toArgb(),
                Colors.Primary.toArgb(),
                false
            )
            .setMenuDoneText(context.getString(R.string.all_send))
            .setActionBarTitleColor(Color.WHITE)
            .startAlbumWithActivityResultCallback(photoGalleryResults)
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
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, message)
        context.startActivity(intent)
    }

    fun call(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$phoneNumber")
        context.startActivity(intent)
    }
}