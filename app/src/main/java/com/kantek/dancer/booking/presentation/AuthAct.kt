package com.kantek.dancer.booking.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kantek.dancer.booking.app.AppComponentAct
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.domain.model.support.Screen
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.provider.NavigationProvider
import com.kantek.dancer.booking.presentation.screen.auth.SignInScreen
import com.kantek.dancer.booking.presentation.screen.auth.SignUpScreen
import com.kantek.dancer.booking.presentation.screen.auth.forgot.ForgotPasswordScreen

class AuthAct : AppComponentAct() {
    @Composable
    override fun ProvideContent() {
        ScopeProvider(Scopes.App) {
            NavigationProvider {
                NavHost(
                    navController = it,
                    startDestination = Screen.SignIn.name,
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable(Screen.SignIn.name) {
                        BackHandler { finish() }
                        SignInScreen(false)
                    }
                    composable(Screen.SignUp.name) {
                        SignUpScreen()
                    }
                    composable(Screen.ForgotPassword.name) {
                        ForgotPasswordScreen()
                    }
                }
            }
        }
    }
}