package com.kantek.dancer.booking.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.core.extensions.safe
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.app.AppComponentAct
import com.kantek.dancer.booking.data.event.AppEvent
import com.kantek.dancer.booking.data.model.firebase.FireBaseCloudMessage
import com.kantek.dancer.booking.app.AppScopes
import com.kantek.dancer.booking.app.DetailCase
import com.kantek.dancer.booking.app.Otp
import com.kantek.dancer.booking.app.ResetPassword
import com.kantek.dancer.booking.app.PhotoViewer
import com.kantek.dancer.booking.app.ChangePassword
import com.kantek.dancer.booking.app.MyProfile
import com.kantek.dancer.booking.app.ContactUs
import com.kantek.dancer.booking.app.Language
import com.kantek.dancer.booking.app.SignIn
import com.kantek.dancer.booking.app.ManageStaffSignIn
import com.kantek.dancer.booking.app.SignUp
import com.kantek.dancer.booking.app.ForgotPassword
import com.kantek.dancer.booking.app.Conversation
import com.kantek.dancer.booking.app.FaqThreadsQuestion
import com.kantek.dancer.booking.app.Booking
import com.kantek.dancer.booking.app.DetailDancer
import com.kantek.dancer.booking.app.DancerList
import com.kantek.dancer.booking.app.PhotosViewer
import com.kantek.dancer.booking.app.BookingConfirm
import com.kantek.dancer.booking.app.Home
import com.kantek.dancer.booking.app.AboutUs
import com.kantek.dancer.booking.app.Terms
import com.kantek.dancer.booking.app.FaqThreads
import com.kantek.dancer.booking.app.Reviews
import com.kantek.dancer.booking.app.CreateReviews
import androidx.navigation.toRoute
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.provider.NavigationProvider
import android.support.ui.provider.PermissionProvider
import com.kantek.dancer.booking.presentation.provider.PopupProvider
import com.kantek.dancer.booking.presentation.screen.account.ContactUsScreen
import com.kantek.dancer.booking.presentation.screen.account.MyProfileScreen
import com.kantek.dancer.booking.presentation.screen.auth.ChangePasswordScreen
import com.kantek.dancer.booking.presentation.screen.auth.GuestSignInScreen
import com.kantek.dancer.booking.presentation.screen.auth.GuestSignUpScreen
import com.kantek.dancer.booking.presentation.screen.auth.ManageStaffSignInScreen
import com.kantek.dancer.booking.presentation.screen.auth.forgot.CreateNewPwScreen
import com.kantek.dancer.booking.presentation.screen.auth.forgot.ForgotPasswordScreen
import com.kantek.dancer.booking.presentation.screen.auth.otp.OTPVerifyScreen
import com.kantek.dancer.booking.presentation.screen.booking.BookingConfirmScreen
import com.kantek.dancer.booking.presentation.screen.booking.BookingScreen
import com.kantek.dancer.booking.presentation.screen.booking.DetailBookingScreen
import com.kantek.dancer.booking.presentation.screen.browser.AboutUsScreen
import com.kantek.dancer.booking.presentation.screen.browser.TermsScreen
import com.kantek.dancer.booking.presentation.screen.conversation.ChatScreen
import com.kantek.dancer.booking.presentation.screen.dancer.DancerListScreen
import com.kantek.dancer.booking.presentation.screen.dancer.DetailDancerScreen
import com.kantek.dancer.booking.presentation.screen.faqs.FAQsThreadsScreen
import com.kantek.dancer.booking.presentation.screen.faqs.QuestionThreadsScreen
import com.kantek.dancer.booking.presentation.screen.home.HomeScreen
import com.kantek.dancer.booking.presentation.screen.language.LanguageScreen
import com.kantek.dancer.booking.presentation.screen.media.PhotoViewerScreen
import com.kantek.dancer.booking.presentation.screen.media.PhotosViewerScreen
import com.kantek.dancer.booking.presentation.screen.review.CreateReviewScreen
import com.kantek.dancer.booking.presentation.screen.review.ReviewScreen
import com.kantek.dancer.booking.presentation.widget.BookingSuccessDialog

class MainAct : AppComponentAct() {
    private var redirectToBookingDetail = mutableStateOf("")
    private var redirectToChat = mutableStateOf("")
    private val roomID by redirectToChat
    private val bookingID by redirectToBookingDetail

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        val bundleData = intent.getBundleExtra("data") ?: return
        redirectToBookingDetail.value =
            bundleData.getString(AppNavigator.Companion.ArgKey.BOOKING_ID, "")
        redirectToChat.value =
            bundleData.getString(AppNavigator.Companion.ArgKey.CONTACT_REQUEST_ID, "")
    }

    @Composable
    override fun ProvideContent() {
        ScopeProvider(AppScopes.App) {
            PermissionProvider {
                PopupProvider {
                    NavigationProvider {
                        NavHost(
                            navController = it,
                            startDestination = Home(),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            composable<Language> { entry ->
                                LanguageScreen(entry.toRoute<Language>().isInApp)
                            }
                            composable<Home> { entry ->
                                BackHandler { showExitAppDialog() }
                                HomeScreen(startTab = entry.toRoute<Home>().homeTab ?: "")
                            }
                            composable<SignIn> {
                                GuestSignInScreen(true)
                            }
                            composable<ManageStaffSignIn> {
                                ManageStaffSignInScreen(true)
                            }
                            composable<SignUp> {
                                GuestSignUpScreen()
                            }
                            composable<ForgotPassword> {
                                ForgotPasswordScreen()
                            }
                            composable<AboutUs> {
                                AboutUsScreen()
                            }
                            composable<Terms> {
                                TermsScreen()
                            }

                            composable<Otp> { entry ->
                                OTPVerifyScreen(entry.toRoute<Otp>().email)
                            }

                            composable<ResetPassword> { entry ->
                                CreateNewPwScreen(entry.toRoute<ResetPassword>().email)
                            }

                            composable<DetailCase> { entry ->
                                DetailBookingScreen(entry.toRoute<DetailCase>().bookingId)
                            }

                            composable<DetailDancer> { entry ->
                                val route = entry.toRoute<DetailDancer>()
                                DetailDancerScreen(
                                    dancerId = route.dancerId,
                                    hasShowButtons = route.hasShowButtons
                                )
                            }
                            composable<PhotoViewer> { entry ->
                                PhotoViewerScreen(entry.toRoute<PhotoViewer>().photoUrl)
                            }

                            composable<PhotosViewer> { entry ->
                                PhotosViewerScreen(entry.toRoute<PhotosViewer>().photos)
                            }
                            composable<ChangePassword> {
                                ChangePasswordScreen()
                            }
                            composable<MyProfile> {
                                MyProfileScreen()
                            }
                            composable<ContactUs> {
                                ContactUsScreen()
                            }

                            composable<Booking> { entry ->
                                val route = entry.toRoute<Booking>()
                                BookingScreen(
                                    dancerId = route.dancerId,
                                    clubId = route.clubId,
                                    openTime = route.openTime,
                                    closeTime = route.closeTime,
                                    hasNow = route.hasNow,
                                    navBackStackEntry = entry
                                )
                            }
                            composable<BookingConfirm> { entry ->
                                val route = entry.toRoute<BookingConfirm>()
                                BookingConfirmScreen(
                                    dancerIds = route.dancerIds,
                                    dancerNames = route.dancerNames,
                                    dancerAvatars = route.dancerAvatars,
                                    roomId = route.roomId,
                                    clubName = route.clubName,
                                    clubImage = route.clubImage,
                                    bookingDate = route.bookingDate,
                                    bookingTime = route.bookingTime,
                                    roomName = route.roomName,
                                    songs = route.songs,
                                    guests = route.guests,
                                    totalAmount = route.totalAmount,
                                    hasNow = route.hasNow,
                                    tableNumber = route.tableNumber,
                                    customerName = route.customerName,
                                    customerPhone = route.customerPhone
                                )
                            }


                            composable<DancerList> { entry ->
                                val route = entry.toRoute<DancerList>()
                                DancerListScreen(
                                    clubId = route.clubId,
                                    pickForBooking = route.pickForBooking,
                                    excludeDancerIds = route.excludeDancerIds.toSet()
                                )
                            }

                            composable<Conversation> { entry ->
                                ChatScreen(entry.toRoute<Conversation>().roomId)
                            }

                            composable<Reviews> { entry ->
                                val route = entry.toRoute<Reviews>()
                                ReviewScreen(route.reviewTotal, route.lawyerId)
                            }

                            composable<CreateReviews> { entry ->
                                CreateReviewScreen(entry.toRoute<CreateReviews>().bookingDto)
                            }

                            composable<FaqThreads> {
                                FAQsThreadsScreen()
                            }

                            composable<FaqThreadsQuestion> { entry ->
                                val route = entry.toRoute<FaqThreadsQuestion>()
                                QuestionThreadsScreen(route.id, route.name)
                            }

                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            LaunchedEffect(Unit) {
                                val hasNotificationPermission = ContextCompat.checkSelfPermission(
                                    this@MainAct,
                                    Manifest.permission.POST_NOTIFICATIONS
                                ) == PackageManager.PERMISSION_GRANTED
                                if (!hasNotificationPermission) {
                                    accessNotification { }
                                }
                            }
                        }

                        val appNavigator = use<AppNavigator>()
//                    LaunchedEffect(roomID) {
//                        if (roomID.isNotEmpty()) {
//                            appNavigator.navigateConversation(roomID)
//                            redirectToChat.intValue = -1
//                        }
//                    }
                        LaunchedEffect(bookingID) {
                            if (bookingID.isNotEmpty()) {
                                appNavigator.navigateDetailCase(bookingID)
                                redirectToBookingDetail.value = ""
                            }
                        }
                        val showCompletedDialog =
                            remember { mutableStateOf<FireBaseCloudMessage?>(null) }
                        val appEvent = remember { get<AppEvent>() }
                        val bookingCompleted by appEvent.onPushBookingCompleted.collectAsState()

                        LaunchedEffect(bookingCompleted) {
                            if (bookingCompleted != null) {
                                showCompletedDialog.value = bookingCompleted
                                appEvent.onPushBookingCompleted.emit(null)
                            }
                        }

                        if (showCompletedDialog.value != null) {
                            val booking = showCompletedDialog.value!!
                            BookingSuccessDialog(
                                title = booking.title.safe(),
                                booking.body.safe(),
                                textConfirm = stringResource(R.string.all_view_detail),
                                onConfirm = {
                                    appNavigator.navigateDetailCase(booking.bookingId)
                                    showCompletedDialog.value = null
                                },
                                onDismiss = { showCompletedDialog.value = null }
                            )
                        }
                    }
                }
            }
        }
    }
}