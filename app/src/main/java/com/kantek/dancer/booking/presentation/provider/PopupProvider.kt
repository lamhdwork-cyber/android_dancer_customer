package com.kantek.dancer.booking.presentation.provider

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.extensions.useRemember
import com.kantek.dancer.booking.presentation.helper.AppPopup
import com.kantek.dancer.booking.presentation.theme.Colors
import org.koin.core.scope.Scope

@Composable
fun Scope.PopupProvider(content: @Composable () -> Unit) {
    val popup = use<AppPopup>()


    val snackbarHostState = remember { SnackbarHostState() }

    useRemember(onForgotten = { popup.update(null) }) {
        popup.update(snackbarHostState)
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.imePadding(),
                snackbar = { snackbarData ->
                    Snackbar(
                        containerColor = Colors.Primary,
                        contentColor = Color.White,
                        actionColor = Color.White,
                        snackbarData = snackbarData
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            content()
        }
    }
}