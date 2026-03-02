package com.kantek.dancer.booking.presentation.helper

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

class ScopedCoroutineScope : CoroutineScope {
    override val coroutineContext: CoroutineContext =
        Dispatchers.Main.immediate + SupervisorJob() + CoroutineExceptionHandler { _, _ -> }
}