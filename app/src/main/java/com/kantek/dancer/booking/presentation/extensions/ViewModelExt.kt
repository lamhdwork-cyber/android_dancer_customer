package com.kantek.dancer.booking.presentation.extensions

import android.support.core.event.ErrorEvent
import android.support.core.event.LoadingEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.cancellation.CancellationException

fun ViewModel.launch(
    loading: LoadingEvent? = null,
    error: ErrorEvent? = null,
    context: CoroutineContext = Dispatchers.IO,
    function: suspend CoroutineScope.() -> Unit
) {
    val handler = CoroutineExceptionHandler { _, throwable ->
        if (throwable !is CancellationException) {
            throwable.printStackTrace()
            error?.post(throwable)
        }
    }
    viewModelScope.launch(context + handler) {
        try {
            loading?.start()
            function()
        } finally {
            loading?.stop()
        }
    }
}
