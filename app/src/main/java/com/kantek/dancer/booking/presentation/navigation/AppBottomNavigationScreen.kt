package com.kantek.dancer.booking.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.ui.graphics.vector.ImageVector
import com.kantek.dancer.booking.R

sealed class AppBottomNavigationScreen(
    val route: String,
    val titleRes: Int,
    val icon: ImageVector
) {
    object Home : AppBottomNavigationScreen("home", R.string.nav_home, Icons.Outlined.Home)
    object Search : AppBottomNavigationScreen("search", R.string.nav_explore, Icons.Outlined.Explore)
    object Dancers : AppBottomNavigationScreen("dancers_admin", R.string.nav_dancers, Icons.Outlined.Groups)
    object Cases : AppBottomNavigationScreen("bookings", R.string.nav_my_booking, Icons.Outlined.CalendarMonth)
    object Notification : AppBottomNavigationScreen(
        "notification",
        R.string.nav_notification,
        Icons.Outlined.Notifications
    )

    object Account :
        AppBottomNavigationScreen("account", R.string.nav_account, Icons.Outlined.AccountCircle)
}
