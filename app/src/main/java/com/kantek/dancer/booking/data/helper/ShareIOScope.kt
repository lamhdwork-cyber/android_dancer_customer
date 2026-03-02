package com.kantek.dancer.booking.data.helper

import android.support.core.CoDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import java.io.Closeable
import kotlin.coroutines.CoroutineContext

class ShareIOScope : CoroutineScope, Closeable {
    override val coroutineContext: CoroutineContext =
        SupervisorJob() + CoDispatcher.io + CoroutineExceptionHandler { _, _ -> }

    override fun close() {
        cancel()
    }

}