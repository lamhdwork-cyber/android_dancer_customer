package com.kantek.dancer.booking.presentation.helper

import androidx.navigation.NavHostController
import com.kantek.dancer.booking.domain.extension.toJson
import com.kantek.dancer.booking.domain.model.response.BookingDTO
import com.kantek.dancer.booking.domain.model.support.BottomNavigationScreen
import com.kantek.dancer.booking.domain.model.support.Screen
import com.kantek.dancer.booking.domain.model.support.Updatable
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.BOOKING_DTO
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.BOOKING_ID
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.EMAIL
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.ID
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.IS_IN_APP
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.LAWYER_DTO
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.LAWYER_ID
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.NAME
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.PHOTOS_URL
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.PHOTO_URL
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.REVIEW_TOTAL
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.ROOM_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

class AppNavigator : Updatable {
    private var navHost: NavHostController? = null

    companion object {
        object ArgKey {
            const val IS_IN_APP = "is_in_app"
            const val BOOKING_ID = "booking_id"
            const val CONTACT_REQUEST_ID = "contact_request_id"
            const val BOOKING_DTO = "booking_dto"
            const val PHOTO_URL = "photo_url"
            const val PHOTOS_URL = "photos_url"
            const val EMAIL = "email"
            const val ROOM_ID = "room_id"
            const val LAWYER_DTO = "lawyer_dto"
            const val LAWYER_ID = "lawyer_id"
            const val REVIEW_TOTAL = "review_total"
            const val NAME = "name"
            const val ID = "id"
        }
    }

    fun back() {
        if (navHost?.currentDestination?.navigatorName != Screen.SignIn.name)
            navHost?.popBackStack()
    }

    override fun update(value: Any?, notify: Boolean) {
        if (value is NavHostController) {
            this.navHost = value
        }
    }

    fun navigateHome() {
        navHost?.navigate(Screen.Home.name)
    }

    fun navigateLanguage(isInApp: Boolean = false) {
        navHost?.navigate("${Screen.Language.name}?$IS_IN_APP=${isInApp}")
    }

    fun navigateSignIn(hasForgotPassword: Boolean = false) {
        navHost?.navigate(Screen.SignIn.name) {
            if (hasForgotPassword)
                popUpTo(Screen.ForgotPassword.name) {
                    inclusive = true
                }
            launchSingleTop = true
        }
    }

    fun navigateSignUp() {
        navHost?.navigate(Screen.SignUp.name)
    }

    fun navigateForgotPassword() {
        navHost?.navigate(Screen.ForgotPassword.name)
    }

    fun navigateDetailCase(bookingID: Int) {
        navHost?.navigate("${Screen.DetailCase.name}?$BOOKING_ID=${bookingID}")
    }

    fun navigateDetailLawyer(
        lawyerID: Int = -1,
        dataJson: String = ""
    ) {
        navHost?.navigate("${Screen.DetailLawyer.name}?$BOOKING_DTO=$dataJson&$LAWYER_ID=$lawyerID")
    }

    fun navigatePhotoViewer(photoURL: String) {
        navHost?.navigate("${Screen.PhotoViewer.name}?$PHOTO_URL=${photoURL}")
    }

    fun navigateChangePassword() {
        navHost?.navigate(Screen.ChangePassword.name)
    }

    fun navigateMyProfile() {
        navHost?.navigate(Screen.MyProfileScreen.name)
    }

    fun navigateContactUs() {
        navHost?.navigate(Screen.ContactUs.name)
    }

    fun navigateSearch() {
        navHost?.navigate(BottomNavigationScreen.Search.route) {
            if (navHost != null) {
                popUpTo(navHost!!.graph.startDestinationId) {
                    saveState = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateAboutUs() {
        navHost?.navigate(Screen.AboutUs.name)
    }

    fun navigateFAQThreads() {
        navHost?.navigate(Screen.FaqThreads.name)
    }

    fun navigateTerms() {
        navHost?.navigate(Screen.Terms.name)
    }

    fun navigateOTPVerify(email: String) {
        navHost?.navigate("${Screen.OTP.name}?$EMAIL=${email}")
    }

    fun navigateResetPassword(email: String) {
        navHost?.navigate("${Screen.ResetPassword.name}?$EMAIL=${email}")
    }

    fun navigateConversation(roomID: Int) {
        val screenName = "${Screen.Conversion.name}?$ROOM_ID=$roomID"
        navHost?.let { controller ->
            CoroutineScope(Dispatchers.Main).launch {
                controller.popBackStack(screenName, inclusive = true)
                yield()
                controller.navigate(screenName)
            }
        }
    }

    fun navigatePhotoViewer(photoURL: List<String>) {
        navHost?.navigate("${Screen.PhotosViewer.name}?$PHOTOS_URL=${photoURL.toJson()}")
    }

    fun navigateQuickRequest(dataJson: String = "") {
        navHost?.navigate("${Screen.QuickRequest.name}?$LAWYER_DTO=${dataJson}")
    }

    fun navigateLawyerList() {
        navHost?.navigate(Screen.RequestWithLawyer.name)
    }

    fun navigateChangeLawyer() {
        navHost?.navigate(Screen.RequestWithLawyer.name) {
            popUpTo(Screen.RequestWithLawyer.name) { inclusive = false }
            launchSingleTop = true
        }
    }

    fun navigateReviewList(id: Int, textTotal: String) {
        navHost?.navigate("${Screen.Reviews.name}?$REVIEW_TOTAL=$textTotal&$LAWYER_ID=$id")
    }

    fun navigateCreateReview(it: BookingDTO?) {
        navHost?.navigate("${Screen.CreateReviews.name}?$BOOKING_DTO=${it.toJson()}") {
            popUpTo("${Screen.DetailCase.name}?$BOOKING_ID=${it?.id}") { inclusive = true }
        }
    }

    fun navigateQuestion(id: Int, name: String) {
        navHost?.navigate("${Screen.FaqThreadsQuestion.name}?$NAME=$name&$ID=$id")
    }
}