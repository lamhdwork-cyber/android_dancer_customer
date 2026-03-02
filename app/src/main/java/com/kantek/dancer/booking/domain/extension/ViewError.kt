package com.kantek.dancer.booking.domain.extension

import androidx.annotation.StringRes

class ResourceException(val resource: Int) : Throwable()

fun resourceError(@StringRes res: Int): Nothing = throw ResourceException(res)