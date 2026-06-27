package com.hdl.dancer.booking.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hdl.dancer.booking.app.AppConfig
import com.hdl.dancer.booking.presentation.model.support.BottomNavigationScreen
import com.hdl.dancer.booking.presentation.model.support.Scopes
import com.hdl.dancer.booking.domain.provider.CurrentUserRoleProvider
import com.hdl.dancer.booking.presentation.extensions.ScopeProvider
import com.hdl.dancer.booking.presentation.screen.account.AccountScreen
import com.hdl.dancer.booking.presentation.screen.booking.MyBookingScreen
import com.hdl.dancer.booking.presentation.screen.club.FindClubScreen
import com.hdl.dancer.booking.presentation.screen.dancer.DancerListOfAdminScreen
import com.hdl.dancer.booking.presentation.screen.notification.NotificationScreen
import com.hdl.dancer.booking.presentation.theme.Colors
import com.hdl.dancer.booking.presentation.widget.AppNavigateBottomBar
import org.koin.compose.koinInject

@Composable
fun HomeScreen(startTab: String = "") = ScopeProvider(Scopes.Home) {
    val roleProvider = koinInject<CurrentUserRoleProvider>()
    val firstTab = if (AppConfig.UserRole.isClubManager(roleProvider.getRole())) {
        null
    } else {
        BottomNavigationScreen.Search
    }
    val firstTabRoute = firstTab?.route ?: BottomNavigationScreen.Cases.route
    val nav = rememberNavController()

    LaunchedEffect(startTab, firstTabRoute) {
        if (startTab.isNotBlank() && startTab != firstTabRoute) {
            nav.navigate(startTab) {
                popUpTo(nav.graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    val navBackStackEntry by nav.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: firstTabRoute

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.Dark120812)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.DarkFF0A050A)
        ) {
            NavHost(
                navController = nav,
                startDestination = firstTabRoute,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Colors.Dark120812)
            ) {
                composable(BottomNavigationScreen.Search.route) { FindClubScreen() }
                composable(BottomNavigationScreen.Dancers.route) { DancerListOfAdminScreen() }
                composable(BottomNavigationScreen.Cases.route) { MyBookingScreen() }
                composable(BottomNavigationScreen.Notification.route) { NotificationScreen() }
                composable(BottomNavigationScreen.Account.route) { AccountScreen() }
            }

            AppNavigateBottomBar(
                selectedItemRouter = currentRoute,
                onItemRouterSelected = { router ->
                    if (currentRoute != router) {
                        nav.navigate(router) {
                            popUpTo(nav.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                firstTab = firstTab
            )
        }
    }
}
