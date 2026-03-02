package com.kantek.dancer.booking.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.core.extensions.safe
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.app.AppComponentAct
import com.kantek.dancer.booking.data.event.AppEvent
import com.kantek.dancer.booking.domain.model.firebase.FireBaseCloudMessage
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.domain.model.support.Screen
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.provider.NavigationProvider
import com.kantek.dancer.booking.presentation.provider.PermissionProvider
import com.kantek.dancer.booking.presentation.provider.PopupProvider
import com.kantek.dancer.booking.presentation.screen.account.ContactUsScreen
import com.kantek.dancer.booking.presentation.screen.account.MyProfileScreen
import com.kantek.dancer.booking.presentation.screen.auth.ChangePasswordScreen
import com.kantek.dancer.booking.presentation.screen.auth.SignInScreen
import com.kantek.dancer.booking.presentation.screen.auth.SignUpScreen
import com.kantek.dancer.booking.presentation.screen.auth.forgot.CreateNewPwScreen
import com.kantek.dancer.booking.presentation.screen.auth.forgot.ForgotPasswordScreen
import com.kantek.dancer.booking.presentation.screen.auth.otp.OTPVerifyScreen
import com.kantek.dancer.booking.presentation.screen.browser.AboutUsScreen
import com.kantek.dancer.booking.presentation.screen.browser.TermsScreen
import com.kantek.dancer.booking.presentation.screen.cases.DetailCaseScreen
import com.kantek.dancer.booking.presentation.screen.conversation.ChatScreen
import com.kantek.dancer.booking.presentation.screen.faqs.FAQsThreadsScreen
import com.kantek.dancer.booking.presentation.screen.faqs.QuestionThreadsScreen
import com.kantek.dancer.booking.presentation.screen.home.HomeScreen
import com.kantek.dancer.booking.presentation.screen.language.LanguageScreen
import com.kantek.dancer.booking.presentation.screen.lawyer.DetailLawyerScreen
import com.kantek.dancer.booking.presentation.screen.media.PhotoViewerScreen
import com.kantek.dancer.booking.presentation.screen.media.PhotosViewerScreen
import com.kantek.dancer.booking.presentation.screen.review.CreateReviewScreen
import com.kantek.dancer.booking.presentation.screen.review.ReviewScreen
import com.kantek.dancer.booking.presentation.screen.search.DancerListScreen
import com.kantek.dancer.booking.presentation.screen.search.QuickRequestScreen
import com.kantek.dancer.booking.presentation.widget.BookingSuccessDialog

class MainAct : AppComponentAct() {
    private var redirectToBookingDetail = mutableIntStateOf(-1)
    private var redirectToChat = mutableIntStateOf(-1)
    private val roomID by redirectToChat
    private val bookingID by redirectToBookingDetail

    /*Apply color of status bar for Android 15*/
    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= 35 && intent?.getBooleanExtra("hasRecreated", false) != true) {
            intent?.putExtra("hasRecreated", true)
            window.decorView.post {
                recreate()
            }
            return
        }
    }

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
        redirectToBookingDetail.intValue =
            bundleData.getInt(AppNavigator.Companion.ArgKey.BOOKING_ID, -1)
        redirectToChat.intValue =
            bundleData.getInt(AppNavigator.Companion.ArgKey.CONTACT_REQUEST_ID, -1)
    }

    @Composable
    override fun ProvideContent() {
        ScopeProvider(Scopes.App) {
            PermissionProvider {
                PopupProvider {
                    NavigationProvider {
                        NavHost(
                            navController = it,
                            startDestination = Screen.Home.name,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            val keyArg = AppNavigator.Companion.ArgKey.IS_IN_APP
                            composable(
                                "${Screen.Language.name}?$keyArg={$keyArg}",
                                arguments = listOf(navArgument(keyArg) {
                                    type = NavType.BoolType
                                    defaultValue = false
                                })
                            ) { backStackEntry ->
                                val isUserLoggedIn =
                                    backStackEntry.arguments?.getBoolean(keyArg)
                                        ?: false
                                LanguageScreen(isUserLoggedIn)
                            }
                            composable(Screen.Home.name) {
                                BackHandler { showExitAppDialog() }
                                HomeScreen()
                            }
                            composable(Screen.SignIn.name) {
                                SignInScreen(true)
                            }
                            composable(Screen.SignUp.name) {
                                SignUpScreen()
                            }
                            composable(Screen.ForgotPassword.name) {
                                ForgotPasswordScreen()
                            }
                            composable(Screen.AboutUs.name) {
                                AboutUsScreen()
                            }
                            composable(Screen.Terms.name) {
                                TermsScreen()
                            }

                            val keyEmailArg =
                                AppNavigator.Companion.ArgKey.EMAIL
                            composable(
                                "${Screen.OTP.name}?$keyEmailArg={$keyEmailArg}",
                                arguments = listOf(navArgument(keyEmailArg) {
                                    type = NavType.StringType
                                })
                            ) { backStackEntry ->
                                val arg =
                                    backStackEntry.arguments?.getString(keyEmailArg)
                                        ?: ""
                                OTPVerifyScreen(arg)
                            }

                            composable(
                                "${Screen.ResetPassword.name}?$keyEmailArg={$keyEmailArg}",
                                arguments = listOf(navArgument(keyEmailArg) {
                                    type = NavType.StringType
                                })
                            ) { backStackEntry ->
                                val arg =
                                    backStackEntry.arguments?.getString(keyEmailArg)
                                        ?: ""
                                CreateNewPwScreen(arg)
                            }

                            val keyBookingIDArg =
                                AppNavigator.Companion.ArgKey.BOOKING_ID
                            composable(
                                "${Screen.DetailCase.name}?$keyBookingIDArg={$keyBookingIDArg}",
                                arguments = listOf(navArgument(keyBookingIDArg) {
                                    type = NavType.IntType
                                })
                            ) { backStackEntry ->
                                val arg =
                                    backStackEntry.arguments?.getInt(keyBookingIDArg)
                                        ?: -1
                                DetailCaseScreen(arg)
                            }

                            val keyBookingArg =
                                AppNavigator.Companion.ArgKey.BOOKING_DTO
                            val keyLawyerIDArg =
                                AppNavigator.Companion.ArgKey.LAWYER_ID
                            composable(
                                "${Screen.DetailLawyer.name}?$keyBookingArg={$keyBookingArg}&$keyLawyerIDArg={$keyLawyerIDArg}",
                                arguments = listOf(navArgument(keyBookingArg) {
                                    type = NavType.StringType
                                }, navArgument(keyLawyerIDArg) {
                                    type = NavType.IntType
                                })
                            ) { backStackEntry ->
                                val arg =
                                    backStackEntry.arguments?.getString(keyBookingArg)
                                        ?: ""
                                val argID =
                                    backStackEntry.arguments?.getInt(keyLawyerIDArg)
                                        ?: -1
                                DetailLawyerScreen(argID, arg)
                            }

                            val keyPhotoArg = AppNavigator.Companion.ArgKey.PHOTO_URL
                            composable(
                                "${Screen.PhotoViewer.name}?$keyPhotoArg={$keyPhotoArg}",
                                arguments = listOf(navArgument(keyPhotoArg) {
                                    type = NavType.StringType
                                })
                            ) { backStackEntry ->
                                val arg =
                                    backStackEntry.arguments?.getString(keyPhotoArg)
                                        ?: ""
                                PhotoViewerScreen(arg)
                            }

                            val keyPhotosArg = AppNavigator.Companion.ArgKey.PHOTOS_URL
                            composable(
                                "${Screen.PhotosViewer.name}?$keyPhotosArg={$keyPhotosArg}",
                                arguments = listOf(navArgument(keyPhotosArg) {
                                    type = NavType.StringType
                                })
                            ) { backStackEntry ->
                                val arg =
                                    backStackEntry.arguments?.getString(keyPhotosArg)
                                        ?: ""
                                PhotosViewerScreen(arg)
                            }
                            composable(Screen.ChangePassword.name) {
                                ChangePasswordScreen()
                            }
                            composable(Screen.MyProfileScreen.name) {
                                MyProfileScreen()
                            }
                            composable(Screen.ContactUs.name) {
                                ContactUsScreen()
                            }

                            val keyLawyerArg =
                                AppNavigator.Companion.ArgKey.LAWYER_DTO
                            composable(
                                "${Screen.QuickRequest.name}?$keyLawyerArg={$keyLawyerArg}",
                                arguments = listOf(navArgument(keyLawyerArg) {
                                    type = NavType.StringType
                                })
                            ) { backStackEntry ->
                                val arg =
                                    backStackEntry.arguments?.getString(keyLawyerArg)
                                        ?: ""
                                QuickRequestScreen(arg)
                            }

                            composable(Screen.RequestWithLawyer.name) {
                                DancerListScreen()
                            }

                            val keyRoomIDArg =
                                AppNavigator.Companion.ArgKey.ROOM_ID
                            composable(
                                "${Screen.Conversion.name}?$keyRoomIDArg={$keyRoomIDArg}",
                                arguments = listOf(navArgument(keyRoomIDArg) {
                                    type = NavType.IntType
                                })
                            ) { backStackEntry ->
                                val arg =
                                    backStackEntry.arguments?.getInt(keyRoomIDArg)
                                        ?: -1
                                ChatScreen(arg)
                            }

                            val keyReviewTotalArg =
                                AppNavigator.Companion.ArgKey.REVIEW_TOTAL
                            composable(
                                "${Screen.Reviews.name}?$keyReviewTotalArg={$keyReviewTotalArg}&$keyLawyerIDArg={$keyLawyerIDArg}",
                                arguments = listOf(navArgument(keyReviewTotalArg) {
                                    type = NavType.StringType
                                }, navArgument(keyLawyerIDArg) {
                                    type = NavType.IntType
                                })
                            ) { backStackEntry ->
                                val arg =
                                    backStackEntry.arguments?.getString(
                                        keyReviewTotalArg
                                    )
                                        ?: ""
                                val argID =
                                    backStackEntry.arguments?.getInt(keyLawyerIDArg)
                                        ?: -1
                                ReviewScreen(arg, argID)
                            }

                            composable(
                                "${Screen.CreateReviews.name}?$keyBookingArg={$keyBookingArg}",
                                arguments = listOf(navArgument(keyBookingArg) {
                                    type = NavType.StringType
                                })
                            ) { backStackEntry ->
                                val arg =
                                    backStackEntry.arguments?.getString(keyBookingArg)
                                        ?: ""
                                CreateReviewScreen(arg)
                            }

                            composable(Screen.FaqThreads.name) {
                                FAQsThreadsScreen()
                            }

                            val keyIDArg =
                                AppNavigator.Companion.ArgKey.ID
                            val keyNameArg =
                                AppNavigator.Companion.ArgKey.NAME
                            composable(
                                "${Screen.FaqThreadsQuestion.name}?$keyNameArg={$keyNameArg}&$keyIDArg={$keyIDArg}",
                                arguments = listOf(navArgument(keyNameArg) {
                                    type = NavType.StringType
                                }, navArgument(keyIDArg) {
                                    type = NavType.IntType
                                })
                            ) { backStackEntry ->
                                val argName =
                                    backStackEntry.arguments?.getString(keyNameArg)
                                        ?: ""
                                val argID =
                                    backStackEntry.arguments?.getInt(keyIDArg)
                                        ?: -1
                                QuestionThreadsScreen(argID, argName)
                            }

                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            accessNotification { }
                        }

                        val appNavigator = use<AppNavigator>(Scopes.App)
                        LaunchedEffect(roomID) {
                            if (roomID > 0) {
                                appNavigator.navigateConversation(roomID)
                                redirectToChat.intValue = -1
                            }
                        }
                        LaunchedEffect(bookingID) {
                            if (bookingID > 0) {
                                appNavigator.navigateDetailCase(bookingID)
                                redirectToBookingDetail.intValue = -1
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
                                    appNavigator.navigateDetailCase(booking.contact_request_id)
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