package com.kantek.dancer.booking.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kantek.dancer.booking.app.AppComponentAct
import com.kantek.dancer.booking.app.AppScopes
import com.kantek.dancer.booking.app.Auth
import com.kantek.dancer.booking.app.SignIn
import com.kantek.dancer.booking.app.ManageStaffSignIn
import com.kantek.dancer.booking.app.SignUp
import com.kantek.dancer.booking.app.ForgotPassword
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
        ScopeProvider(AppScopes.App) {
            NavigationProvider {
                NavHost(
                    navController = it,
                    startDestination = Auth,
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable<Auth> {
                        AuthScreen()
                    }
                    composable<SignIn> {
                        GuestSignInScreen(false)
                    }
                    composable<ManageStaffSignIn> {
                        ManageStaffSignInScreen(false)
                    }
                    composable<SignUp> {
                        GuestSignUpScreen()
                    }
                    composable<ForgotPassword> {
                        ForgotPasswordScreen()
                    }
                }
            }
        }
    }
}