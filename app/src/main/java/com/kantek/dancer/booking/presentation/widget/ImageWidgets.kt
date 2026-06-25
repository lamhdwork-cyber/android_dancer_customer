package com.kantek.dancer.booking.presentation.widget

import android.support.ui.widget.AppImageDef
import android.support.ui.widget.AvatarImageDef
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kantek.dancer.booking.R

/**
 * App wrapper around [AppImageDef] that supplies the app's default placeholder /
 * error image (launcher icon). Screens use this so they don't need to pass them.
 */
@Composable
fun AppImage(
    url: String?,
    placeholderRes: Int = R.mipmap.ic_launcher,
    errorRes: Int = R.mipmap.ic_launcher,
    modifier: Modifier = Modifier,
    isShowLoading: Boolean = true,
    contentScale: ContentScale = ContentScale.Crop
) {
    AppImageDef(
        url = url,
        placeholderRes = placeholderRes,
        errorRes = errorRes,
        modifier = modifier,
        isShowLoading = isShowLoading,
        contentScale = contentScale
    )
}

/**
 * App wrapper around [AvatarImageDef] with the app's default placeholder / error
 * image (launcher icon).
 */
@Composable
fun AvatarImage(
    url: String?,
    size: Dp = 70.dp,
    onClick: (() -> Unit)? = null,
    placeholderRes: Int = R.mipmap.ic_launcher,
    errorRes: Int = R.mipmap.ic_launcher
) {
    AvatarImageDef(
        url = url,
        size = size,
        onClick = onClick,
        placeholderRes = placeholderRes,
        errorRes = errorRes
    )
}
