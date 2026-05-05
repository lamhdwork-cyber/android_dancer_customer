package com.kantek.dancer.booking.domain.model.support

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.ui.graphics.vector.ImageVector
import com.kantek.dancer.booking.R

enum class Screen {
    Auth,
    SignIn,
    ManageStaffSignIn,
    SignUp,
    ForgotPassword,
    Home,
    DetailCase,
    DetailDancer,
    Booking,
    BookingConfirm,
    PhotoViewer,
    PhotosViewer,
    ChangePassword,
    MyProfileScreen,
    ContactUs,
    AboutUs,
    FaqThreads,
    FaqThreadsQuestion,
    Terms,
    OTP,
    ResetPassword,
    Conversion,
    DancerList,
    Reviews,
    CreateReviews,
    Language;
}

sealed class BottomNavigationScreen(
    val route: String,
    val titleRes: Int,
    val icon: ImageVector
) {
    object Home : BottomNavigationScreen("home", R.string.nav_home, Icons.Outlined.Home)
    object Search : BottomNavigationScreen("search", R.string.nav_explore, Icons.Outlined.Explore)
    object Cases : BottomNavigationScreen("bookings", R.string.nav_my_booking, Icons.Outlined.CalendarMonth)
    object Notification : BottomNavigationScreen(
        "notification",
        R.string.nav_notification,
        Icons.Outlined.Notifications
    )

    object Account :
        BottomNavigationScreen("account", R.string.nav_account, Icons.Outlined.AccountCircle)
}