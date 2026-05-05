package com.kantek.dancer.booking.presentation.helper

import android.net.Uri
import androidx.navigation.NavHostController
import com.kantek.dancer.booking.domain.extension.toJson
import com.kantek.dancer.booking.domain.model.response.BookingDTO
import com.kantek.dancer.booking.domain.model.support.BottomNavigationScreen
import com.kantek.dancer.booking.domain.model.support.Screen
import com.kantek.dancer.booking.domain.model.support.Updatable
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.BOOKING_DATE
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.BOOKING_DTO
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.BOOKING_ID
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.BOOKING_TIME
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.CLUB_ID
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.CLUB_IMAGE
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.CLUB_NAME
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.DANCER_AVATARS
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.DANCER_IDS
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.DANCER_NAMES
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.EMAIL
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.EXCLUDE_DANCER_IDS
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.GUESTS
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.HAS_NOW
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.HOME_TAB
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.ID
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.IS_IN_APP
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.LAWYER_DTO
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.LAWYER_ID
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.NAME
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.PHOTOS_URL
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.PHOTO_URL
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.PICKED_DANCER_ID
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.PICK_FOR_BOOKING
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.REVIEW_TOTAL
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.ROOM_ID
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.ROOM_NAME
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.SONGS
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.TOTAL_AMOUNT
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
            const val CLUB_ID = "club_id"
            const val HAS_NOW = "has_now"
            const val DANCER_IDS = "dancer_ids"
            const val DANCER_NAMES = "dancer_names"
            const val DANCER_AVATARS = "dancer_avatars"
            const val CLUB_NAME = "club_name"
            const val CLUB_IMAGE = "club_image"
            const val BOOKING_DATE = "booking_date"
            const val BOOKING_TIME = "booking_time"
            const val ROOM_NAME = "room_name"
            const val SONGS = "songs"
            const val GUESTS = "guests"
            const val TOTAL_AMOUNT = "total_amount"
            const val HOME_TAB = "home_tab"
            const val PICK_FOR_BOOKING = "pick_for_booking"
            const val EXCLUDE_DANCER_IDS = "exclude_dancer_ids"
            const val PICKED_DANCER_ID = "picked_dancer_id"
        }
    }

    fun back() {
        navHost?.let { controller ->
            if (!controller.popBackStack()) {
                controller.navigateUp()
            }
        }
    }

    override fun update(value: Any?, notify: Boolean) {
        if (value is NavHostController) {
            this.navHost = value
        }
    }

    fun navigateHome() {
        navHost?.navigate(Screen.Home.name)
    }

    fun navigateHomeAndClearStack() {
        navHost?.let { controller ->
            CoroutineScope(Dispatchers.Main).launch {
                controller.navigate(Screen.Home.name) {
                    popUpTo(Screen.Home.name) {
                        inclusive = false
                        saveState = false
                    }
                    launchSingleTop = true
                }
            }
        }
    }

    fun navigateHomeMyBookings() {
        val homeTabArg = BottomNavigationScreen.Cases.route
        navHost?.let { controller ->
            CoroutineScope(Dispatchers.Main).launch {
                controller.navigate("${Screen.Home.name}?$HOME_TAB=$homeTabArg") {
                    popUpTo(Screen.Home.name) {
                        inclusive = false
                        saveState = false
                    }
                    launchSingleTop = true
                }
            }
        }
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

    fun navigateManageStaffSignIn() {
        navHost?.navigate(Screen.ManageStaffSignIn.name) {
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
        dataJson: String = "",
        dancerId: String = ""
    ) {
        navHost?.navigate("${Screen.DetailDancer.name}?$BOOKING_DTO=$dataJson&$LAWYER_ID=$lawyerID&$ID=$dancerId")
    }

    fun navigateDetailDancer(dancerId: String) {
        navHost?.navigate("${Screen.DetailDancer.name}?$ID=$dancerId")
    }

    fun navigateBooking(
        dancerId: String = "",
        hasNow: Boolean = true,
        clubId: String = ""
    ) {
        val clubArg = if (clubId.isBlank()) "" else Uri.encode(clubId)
        val dancerArg = Uri.encode(dancerId)
        navHost?.navigate("${Screen.Booking.name}?$ID=$dancerArg&$HAS_NOW=$hasNow&$CLUB_ID=$clubArg")
    }

    fun navigateBookingConfirm(
        dancerIds: List<String>,
        dancerNames: List<String>,
        dancerAvatars: List<String>,
        roomId: String,
        clubName: String,
        clubImage: String,
        bookingDate: String,
        bookingTime: String,
        roomName: String,
        songs: Int,
        guests: Int,
        totalAmount: String,
        hasNow: Boolean
    ) {
        val dancerIdsArg = Uri.encode(dancerIds.joinToString(","))
        val dancerNamesArg = Uri.encode(dancerNames.joinToString("|,|"))
        val dancerAvatarsArg = Uri.encode(dancerAvatars.joinToString("|,|"))
        val roomIdArg = Uri.encode(roomId)
        val clubNameArg = Uri.encode(clubName)
        val clubImageArg = Uri.encode(clubImage)
        val bookingDateArg = Uri.encode(bookingDate)
        val bookingTimeArg = Uri.encode(bookingTime)
        val roomNameArg = Uri.encode(roomName)
        val totalAmountArg = Uri.encode(totalAmount)
        navHost?.navigate(
            "${Screen.BookingConfirm.name}?$DANCER_IDS=$dancerIdsArg&$DANCER_NAMES=$dancerNamesArg&$DANCER_AVATARS=$dancerAvatarsArg&$ROOM_ID=$roomIdArg&$CLUB_NAME=$clubNameArg&$CLUB_IMAGE=$clubImageArg&$BOOKING_DATE=$bookingDateArg&$BOOKING_TIME=$bookingTimeArg&$ROOM_NAME=$roomNameArg&$SONGS=$songs&$GUESTS=$guests&$TOTAL_AMOUNT=$totalAmountArg&$HAS_NOW=$hasNow"
        )
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

    fun navigateDancerList(clubId: String) {
        navHost?.navigate("${Screen.DancerList.name}?$CLUB_ID=${Uri.encode(clubId)}&$PICK_FOR_BOOKING=false&$EXCLUDE_DANCER_IDS=")
    }

    fun navigateDancerListForBookingPick(clubId: String, excludeDancerIds: List<String>) {
        val exclude = Uri.encode(excludeDancerIds.joinToString(","))
        navHost?.navigate(
            "${Screen.DancerList.name}?$CLUB_ID=${Uri.encode(clubId)}&$PICK_FOR_BOOKING=true&$EXCLUDE_DANCER_IDS=$exclude"
        )
    }

    fun finishBookingDancerPick(dancerId: String) {
        navHost?.previousBackStackEntry?.savedStateHandle?.set(PICKED_DANCER_ID, dancerId)
        navHost?.popBackStack()
    }

    fun navigateChangeLawyer() {
        val route =
            "${Screen.DancerList.name}?$CLUB_ID=&$PICK_FOR_BOOKING=false&$EXCLUDE_DANCER_IDS="
        navHost?.navigate(route) {
            popUpTo(Screen.DancerList.name) { inclusive = false }
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