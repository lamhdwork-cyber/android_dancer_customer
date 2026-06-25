package android.support.ui.provider

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.provider.Settings
import android.support.ui.R
import android.support.ui.app.AppPermission
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri

@Composable
fun PermissionProvider(content: @Composable AppPermission.() -> Unit) {
    val context = LocalContext.current
    val activity = context as? ComponentActivity ?: error("PermissionProvider only support for Activity!")

    var onGranted by remember { mutableStateOf<(() -> Unit)?>(null) }
    var onDenied by remember { mutableStateOf<(() -> Unit)?>(null) }

    var recheckPermissions by remember { mutableStateOf<Map<String, Int>>(emptyMap()) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        val grantedPermissions = results.filterValues { it }.keys
        val deniedPermissions = results.filterValues { !it }.keys

        recheckPermissions = recheckPermissions.toMutableMap().apply {
            deniedPermissions.forEach { permission ->
                this[permission] = (this[permission] ?: 0) + 1
            }
        }

        val newPermanentlyDenied = deniedPermissions.filter { permission ->
            (recheckPermissions[permission] ?: 0) > 1
                    && !ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
        }.toSet()


        when {
            grantedPermissions.isNotEmpty() && deniedPermissions.isEmpty() -> {
                onGranted?.invoke()
            }

            newPermanentlyDenied.isNotEmpty() -> {
                showPermissionDialog(activity)
            }

            else -> {
                onDenied?.invoke()
            }
        }
    }

    val scope = remember {
        AppPermission { permissions, granted, denied ->
            onGranted = granted
            onDenied = denied
            permissionLauncher.launch(permissions)
        }
    }

    scope.content()
}

fun showPermissionDialog(activity: Activity) {
    AlertDialog.Builder(activity)
        .setTitle(activity.getString(R.string.core_permission_title))
        .setMessage(activity.getString(R.string.core_permission_des))
        .setPositiveButton(activity.getString(R.string.core_permission_settings)) { _, _ ->
            activity.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = ("package:" + activity.packageName).toUri()
            })
        }
        .setNegativeButton(activity.getString(R.string.core_permission_cancel), null)
        .show()
}
