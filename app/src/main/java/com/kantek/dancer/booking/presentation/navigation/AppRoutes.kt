package com.kantek.dancer.booking.presentation.navigation

import kotlinx.serialization.Serializable

/** Type-safe navigation routes (kotlinx.serialization) for the whole app. */

@Serializable
data class DetailCase(val bookingId: String)

@Serializable
data class Otp(val email: String)

@Serializable
data class ResetPassword(val email: String)

@Serializable
data class PhotoViewer(val photoUrl: String)

@Serializable
data object ChangePassword

@Serializable
data object MyProfile

@Serializable
data object ContactUs

@Serializable
data class Language(val isInApp: Boolean = false)

@Serializable
data object SignIn

@Serializable
data object ManageStaffSignIn

@Serializable
data object SignUp

@Serializable
data object ForgotPassword

@Serializable
data class Conversation(val roomId: Int)

@Serializable
data class FaqThreadsQuestion(val name: String, val id: Int)

@Serializable
data class Booking(
    val dancerId: String = "",
    val hasNow: Boolean = true,
    val clubId: String = "",
    val openTime: String = "",
    val closeTime: String = ""
)

@Serializable
data class DetailDancer(
    val dancerId: String = "",
    val hasShowButtons: Boolean = true
)

@Serializable
data class DancerList(
    val clubId: String = "",
    val pickForBooking: Boolean = false,
    val excludeDancerIds: List<String> = emptyList()
)

@Serializable
data class PhotosViewer(val photos: List<String> = emptyList())

@Serializable
data class Home(val homeTab: String? = null)

@Serializable
data object Auth

@Serializable
data object AboutUs

@Serializable
data object Terms

@Serializable
data object FaqThreads

@Serializable
data class Reviews(val reviewTotal: String = "", val lawyerId: Int = -1)

@Serializable
data class CreateReviews(val bookingDto: String = "")

@Serializable
data class BookingConfirm(
    val dancerIds: String = "",
    val dancerNames: String = "",
    val dancerAvatars: String = "",
    val roomId: String = "",
    val clubName: String = "",
    val clubImage: String = "",
    val bookingDate: String = "",
    val bookingTime: String = "",
    val roomName: String = "",
    val songs: Int = 1,
    val guests: Int = 1,
    val totalAmount: String = "0",
    val hasNow: Boolean = true,
    val tableNumber: String = "",
    val customerName: String = "",
    val customerPhone: String = ""
)
