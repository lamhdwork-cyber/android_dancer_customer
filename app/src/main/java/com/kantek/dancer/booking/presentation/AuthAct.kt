package com.kantek.dancer.booking.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kantek.dancer.booking.app.AppComponentAct
import com.kantek.dancer.booking.presentation.model.support.Scopes
import com.kantek.dancer.booking.presentation.model.support.Screen
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.provider.NavigationProvider
import com.kantek.dancer.booking.presentation.screen.auth.AuthScreen
import com.kantek.dancer.booking.presentation.screen.auth.GuestSignInScreen
import com.kantek.dancer.booking.presentation.screen.auth.GuestSignUpScreen
import com.kantek.dancer.booking.presentation.screen.auth.ManageStaffSignInScreen
import com.kantek.dancer.booking.presentation.screen.auth.forgot.ForgotPasswordScreen

class AuthAct : AppComponentAct() {
    @Composable
    override fun ProvideContent() {
        ScopeProvider(Scopes.App) {
            NavigationProvider {
                NavHost(
                    navController = it,
                    startDestination = Screen.Auth.name,
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable(Screen.Auth.name) {
                        AuthScreen()
                    }
                    composable(Screen.SignIn.name) {
                        GuestSignInScreen(false)
                    }
                    composable(Screen.ManageStaffSignIn.name) {
                        ManageStaffSignInScreen(false)
                    }
                    composable(Screen.SignUp.name) {
                        GuestSignUpScreen()
                    }
                    composable(Screen.ForgotPassword.name) {
                        ForgotPasswordScreen()
                    }
                }
            }
        }
    }
}