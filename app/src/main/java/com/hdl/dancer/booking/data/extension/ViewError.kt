package com.hdl.dancer.booking.data.extension

import androidx.annotation.StringRes

class ResourceException(val resource: Int) : Throwable()

fun resourceError(@StringRes res: Int): Nothing = throw ResourceException(res)