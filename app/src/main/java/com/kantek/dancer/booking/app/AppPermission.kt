package com.kantek.dancer.booking.app

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi

class AppPermission(private val requestPermissions: (Array<String>, () -> Unit, () -> Unit) -> Unit) {
    private fun requestPermissions(
        vararg permissions: String,
        onGranted: () -> Unit = {},
        onDenied: () -> Unit = {}
    ) {
        requestPermissions(arrayOf(*permissions), onGranted, onDenied)
    }

    fun accessCapture(function: () -> Unit) {
        return requestPermissions(
            *PERMISSION_CAPTURE,
            onGranted = function
        )
    }

    fun accessCallPhone(function: () -> Unit) {
        return requestPermissions(
            Manifest.permission.CALL_PHONE,
            onGranted = function
        )
    }

    fun accessReadImage(function: () -> Unit) {
        return requestPermissions(
            *PERMISSION_READ_IMAGE,
            onGranted = function
        )
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun accessNotification(function: () -> Unit) {
        return requestPermissions(
            Manifest.permission.POST_NOTIFICATIONS,
            onGranted = function
        )
    }

    companion object {
        val PERMISSION_CAPTURE = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(Manifest.permission.CAMERA)
        } else {
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }

        val PERMISSION_READ_IMAGE = arrayOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Manifest.permission.READ_MEDIA_IMAGES
            else Manifest.permission.READ_EXTERNAL_STORAGE
        )

    }
}