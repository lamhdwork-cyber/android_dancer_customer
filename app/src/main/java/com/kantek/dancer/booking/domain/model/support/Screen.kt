package com.kantek.dancer.booking.domain.model.support

import com.kantek.dancer.booking.R

enum class Screen {
    SignIn,
    SignUp,
    ForgotPassword,
    Home,
    DetailCase,
    DetailLawyer,
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
    QuickRequest,
    RequestWithLawyer,
    Reviews,
    CreateReviews,
    Language;
}

sealed class BottomNavigationScreen(
    val route: String,
    val titleRes: Int,
    val icon: Int
) {
    object Home : BottomNavigationScreen("home", R.string.nav_home, R.drawable.ic_nav_home)
    object Search : BottomNavigationScreen("search", R.string.nav_search, R.drawable.ic_nav_search)
    object Cases : BottomNavigationScreen("bookings", R.string.nav_my_booking, R.drawable.ic_nav_cases)
    object Notification : BottomNavigationScreen(
        "notification",
        R.string.nav_notification,
        R.drawable.ic_nav_notification
    )

    object Account :
        BottomNavigationScreen("account", R.string.nav_account, R.drawable.ic_nav_account)
}