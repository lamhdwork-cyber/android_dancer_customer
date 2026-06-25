package android.support.ui.widget

import android.support.ui.R
import android.support.ui.theme.CoreColors
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.launch

@Composable
fun AppDialog(
    title: String,
    message: String,
    textConfirm: String = stringResource(R.string.core_all_confirm),
    textDismiss: String = stringResource(R.string.core_all_cancel),
    onConfirm: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null
) {
    AlertDialog(
        onDismissRequest = { onDismiss?.invoke() },
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = {
            onConfirm?.let {
                Button(
                    onClick = onConfirm,
                    modifier = Modifier.widthIn(min = 80.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CoreColors.Primary, contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 10.dp,
                        pressedElevation = 10.dp,
                        focusedElevation = 10.dp,
                        hoveredElevation = 10.dp
                    )
                ) {
                    Text(textConfirm)
                }
            }
        },
        dismissButton = {
            onDismiss?.let {
                TextButton(onClick = it) {
                    Text(textDismiss, color = CoreColors.Gray146)
                }
            }
        })
}

@Composable
fun AppNotificationDialog(
    message: String, hasKillApp: Boolean = false, onDismiss: (Boolean) -> Unit
) {
    AppDialog(
        title = stringResource(R.string.core_all_notification),
        message = message,
        textConfirm = stringResource(R.string.core_all_ok),
        onConfirm = { onDismiss(hasKillApp) })
}

@Composable
fun AppConfirmDialog(
    title: String = stringResource(R.string.core_all_notification),
    message: String,
    textConfirm: String,
    onConfirm: (Boolean) -> Unit,
    hasKillApp: Boolean = false,
    onDismiss: (() -> Unit)? = null
) {
    AppDialog(
        title = title,
        message = message,
        textConfirm = textConfirm,
        onDismiss = onDismiss,
        onConfirm = { onConfirm(hasKillApp) })
}

@Composable
fun LoadingView(
    isShowing: Boolean,
    textLoadingRes: Int = R.string.core_all_loading,
    onDismissRequest: () -> Unit = {}
) {
    if (isShowing) {
        Dialog(onDismissRequest = onDismissRequest) {
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp), contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CircularProgressIndicator(
                        color = CoreColors.Primary,
                        strokeWidth = 4.dp,
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = stringResource(id = textLoadingRes),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
fun LogoutDialog(
    onLogout: () -> Unit,
    onDismiss: () -> Unit
) {
    AppDialog(
        title = stringResource(R.string.all_logout),
        message = stringResource(R.string.message_logout_app),
        textConfirm = stringResource(R.string.all_logout),
        onDismiss = onDismiss,
        onConfirm = onLogout
    )
}

@Composable
fun CallPhoneDialog(
    phoneNumber: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AppConfirmDialog(
        message = stringResource(R.string.msg_confirm_call_phone_s, phoneNumber),
        textConfirm = stringResource(R.string.all_call),
        onConfirm = { onConfirm() },
        onDismiss = { onDismiss() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppPhotoPickerDialog(
    sheetState: SheetState,
    onGalleryClick: () -> Unit,
    onCameraClick: () -> Unit,
    onDismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onDismiss() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.choose_avatar),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onDismiss()
                                onCameraClick()
                            }
                        }
                    },
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.padding(start = 16.dp),
                    imageVector = Icons.Filled.PhotoCamera,
                    contentDescription = "Camera",
                    tint = Color.Black
                )

                Text(
                    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp, end = 16.dp),
                    text = stringResource(R.string.photo_from_camera),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onDismiss()
                                onGalleryClick()
                            }
                        }
                    },
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.padding(start = 16.dp),
                    imageVector = Icons.Filled.PhotoLibrary,
                    contentDescription = "Gallery",
                    tint = Color.Black
                )

                Text(
                    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp, end = 16.dp),
                    text = stringResource(R.string.photo_from_gallery),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            }
        }
    }
}
