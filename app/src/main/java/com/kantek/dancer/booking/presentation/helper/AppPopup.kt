package com.kantek.dancer.booking.presentation.helper

import androidx.compose.material3.SnackbarHostState
import com.kantek.dancer.booking.domain.model.support.Updatable

class AppPopup : Updatable {

    private var snackbarHostState: SnackbarHostState? = null

    suspend fun showIfError(
        onLoading: suspend (Boolean) -> Unit,
        function: suspend () -> Unit
    ) {
        try {
            onLoading(true)
            function()
            onLoading(false)
        } catch (e: Throwable) {
            onLoading(false)
            snackbarHostState?.showSnackbar(e.message ?: "")
        }
    }

    override fun update(value: Any?, notify: Boolean) {
        snackbarHostState = if (value is SnackbarHostState) value else null
    }

    suspend fun show(s: String) {
        snackbarHostState?.showSnackbar(s)
    }

}