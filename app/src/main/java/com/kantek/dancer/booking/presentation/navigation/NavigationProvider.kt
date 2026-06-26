package com.kantek.dancer.booking.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import android.support.ui.helper.edit
import com.kantek.dancer.booking.presentation.extensions.use
import org.koin.core.scope.Scope

@Composable
fun Scope.NavigationProvider(function: @Composable (NavHostController) -> Unit) {
    val appNavigator = use<AppNavigator>()
    val navController = rememberNavController()
    DisposableEffect(appNavigator, navController) {
        appNavigator.edit()?.update(navController)
        onDispose { }
    }
    function(navController)
}