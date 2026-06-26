package com.kantek.dancer.booking.presentation.helper

import android.support.ui.helper.Updatable
import androidx.navigation.NavHostController
import com.kantek.dancer.booking.app.AppBottomNavigationScreen
import com.kantek.dancer.booking.app.Booking
import com.kantek.dancer.booking.app.BookingConfirm
import com.kantek.dancer.booking.app.Home
import com.kantek.dancer.booking.app.ChangePassword
import com.kantek.dancer.booking.app.ContactUs
import com.kantek.dancer.booking.app.Conversation
import com.kantek.dancer.booking.app.DancerList
import com.kantek.dancer.booking.app.DetailCase
import com.kantek.dancer.booking.app.DetailDancer
import com.kantek.dancer.booking.app.FaqThreadsQuestion
import com.kantek.dancer.booking.app.ForgotPassword
import com.kantek.dancer.booking.app.Language
import com.kantek.dancer.booking.app.ManageStaffSignIn
import com.kantek.dancer.booking.app.MyProfile
import com.kantek.dancer.booking.app.Otp
import com.kantek.dancer.booking.app.PhotoViewer
import com.kantek.dancer.booking.app.PhotosViewer
import com.kantek.dancer.booking.app.ResetPassword
import com.kantek.dancer.booking.app.SignIn
import com.kantek.dancer.booking.app.SignUp
import com.kantek.dancer.booking.presentation.helper.AppNavigator.Companion.ArgKey.PICKED_DANCER_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

class AppNavigator : Updatable {
    private var navHost: NavHostController? = null

    companion object {
        object ArgKey {
            // Keys still used outside type-safe routes (push payload bundle + saved-state handle).
            const val BOOKING_ID = "booking_id"
            const val CONTACT_REQUEST_ID = "contact_request_id"
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
        navHost?.navigate(Home())
    }

    fun navigateHomeAndClearStack() {
        navHost?.let { controller ->
            CoroutineScope(Dispatchers.Main).launch {
                controller.navigate(Home()) {
                    popUpTo(Home()) {
                        inclusive = false
                        saveState = false
                    }
                    launchSingleTop = true
                }
            }
        }
    }

    fun navigateHomeMyBookings() {
        navHost?.let { controller ->
            CoroutineScope(Dispatchers.Main).launch {
                controller.navigate(Home(homeTab = AppBottomNavigationScreen.Cases.route)) {
                    popUpTo(Home()) {
                        inclusive = false
                        saveState = false
                    }
                    launchSingleTop = true
                }
            }
        }
    }

    fun navigateLanguage(isInApp: Boolean = false) {
        navHost?.navigate(Language(isInApp))
    }

    fun navigateSignIn(hasForgotPassword: Boolean = false) {
        navHost?.navigate(SignIn) {
            if (hasForgotPassword)
                popUpTo(ForgotPassword) {
                    inclusive = true
                }
            launchSingleTop = true
        }
    }

    fun navigateManageStaffSignIn() {
        navHost?.navigate(ManageStaffSignIn) {
            launchSingleTop = true
        }
    }

    fun navigateSignUp() {
        navHost?.navigate(SignUp)
    }

    fun navigateForgotPassword() {
        navHost?.navigate(ForgotPassword)
    }

    fun navigateDetailCase(bookingID: String) {
        navHost?.navigate(DetailCase(bookingID))
    }

    fun navigateDetailCaseAfterBooking(bookingID: String) {
        navHost?.navigate(DetailCase(bookingID)) {
            popUpTo(Home()) {
                inclusive = false
                saveState = false
            }
            launchSingleTop = true
        }
    }

    fun navigateDetailDancer(dancerId: String, hasShowButtons: Boolean = true) {
        navHost?.navigate(DetailDancer(dancerId = dancerId, hasShowButtons = hasShowButtons))
    }

    fun navigateBooking(
        dancerId: String = "",
        hasNow: Boolean = true,
        clubId: String = "",
        openTime: String = "",
        closeTime: String = ""
    ) {
        navHost?.navigate(
            Booking(
                dancerId = dancerId,
                hasNow = hasNow,
                clubId = clubId,
                openTime = openTime,
                closeTime = closeTime
            )
        )
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
        hasNow: Boolean,
        tableNumber: String,
        customerName: String,
        customerPhone: String
    ) {
        navHost?.navigate(
            BookingConfirm(
                dancerIds = dancerIds.joinToString(","),
                dancerNames = dancerNames.joinToString("|,|"),
                dancerAvatars = dancerAvatars.joinToString("|,|"),
                roomId = roomId,
                clubName = clubName,
                clubImage = clubImage,
                bookingDate = bookingDate,
                bookingTime = bookingTime,
                roomName = roomName,
                songs = songs,
                guests = guests,
                totalAmount = totalAmount,
                hasNow = hasNow,
                tableNumber = tableNumber,
                customerName = customerName,
                customerPhone = customerPhone
            )
        )
    }

    fun navigatePhotoViewer(photoURL: String) {
        navHost?.navigate(PhotoViewer(photoURL))
    }

    fun navigateChangePassword() {
        navHost?.navigate(ChangePassword)
    }

    fun navigateMyProfile() {
        navHost?.navigate(MyProfile)
    }

    fun navigateContactUs() {
        navHost?.navigate(ContactUs)
    }

    fun navigateSearch() {
        navHost?.navigate(AppBottomNavigationScreen.Search.route) {
            if (navHost != null) {
                popUpTo(navHost!!.graph.startDestinationId) {
                    saveState = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateOTPVerify(email: String) {
        navHost?.navigate(Otp(email))
    }

    fun navigateResetPassword(email: String) {
        navHost?.navigate(ResetPassword(email))
    }

    fun navigateConversation(roomID: Int) {
        navHost?.let { controller ->
            CoroutineScope(Dispatchers.Main).launch {
                controller.popBackStack(Conversation(roomID), inclusive = true)
                yield()
                controller.navigate(Conversation(roomID))
            }
        }
    }

    fun navigatePhotoViewer(photoURL: List<String>) {
        navHost?.navigate(PhotosViewer(photoURL))
    }

    fun navigateDancerList(clubId: String) {
        navHost?.navigate(DancerList(clubId = clubId))
    }

    fun navigateDancerListForBookingPick(clubId: String, excludeDancerIds: List<String>) {
        navHost?.navigate(
            DancerList(
                clubId = clubId,
                pickForBooking = true,
                excludeDancerIds = excludeDancerIds
            )
        )
    }

    fun finishBookingDancerPick(dancerId: String) {
        navHost?.previousBackStackEntry?.savedStateHandle?.set(PICKED_DANCER_ID, dancerId)
        navHost?.popBackStack()
    }

    fun navigateQuestion(id: Int, name: String) {
        navHost?.navigate(FaqThreadsQuestion(name = name, id = id))
    }
}