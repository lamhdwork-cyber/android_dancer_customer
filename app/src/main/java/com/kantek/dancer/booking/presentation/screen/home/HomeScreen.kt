package com.kantek.dancer.booking.presentation.screen.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kantek.dancer.booking.app.AppConfig
import com.kantek.dancer.booking.data.repo.LanguageRepo
import com.kantek.dancer.booking.domain.model.support.BottomNavigationScreen
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.provider.NavigationProvider
import com.kantek.dancer.booking.presentation.screen.account.AccountScreen
import com.kantek.dancer.booking.presentation.screen.cases.MyBookingScreen
import com.kantek.dancer.booking.presentation.screen.notification.NotificationScreen
import com.kantek.dancer.booking.presentation.screen.search.FindDancerScreen
import com.kantek.dancer.booking.presentation.widget.AppBottomBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen() = ScopeProvider(Scopes.Home) {
    val languageRepo by inject<LanguageRepo>()

    NavigationProvider { nav ->
        val navBackStackEntry by nav.currentBackStackEntryAsState()
        val currentRoute =
            navBackStackEntry?.destination?.route ?: BottomNavigationScreen.Home.route

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            bottomBar = {
                AppBottomBar(currentRoute) { router ->
                    if (currentRoute != router)
                        nav.navigate(router) {
                            popUpTo(nav.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(bottom = paddingValues.calculateBottomPadding())
            ) {
                NavHost(
                    navController = nav,
                    startDestination = BottomNavigationScreen.Home.route,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    composable(BottomNavigationScreen.Home.route) { FAQsScreen() }
                    composable(BottomNavigationScreen.Search.route) { FindDancerScreen() }
                    composable(BottomNavigationScreen.Cases.route) { MyBookingScreen() }
                    composable(BottomNavigationScreen.Notification.route) { NotificationScreen() }
                    composable(BottomNavigationScreen.Account.route) { AccountScreen() }
                }
            }
        }
    }
}

