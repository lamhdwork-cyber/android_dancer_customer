package android.support.ui.exception

import androidx.annotation.StringRes

/** Error carrying a string resource id to be shown to the user. */
class ResourceException(val resource: Int) : Throwable()

fun resourceError(@StringRes res: Int): Nothing = throw ResourceException(res)
