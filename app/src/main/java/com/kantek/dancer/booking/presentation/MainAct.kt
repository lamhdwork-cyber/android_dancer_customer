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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
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
    private var redirectToBookingDetail = mutableIntStateOf(-1)
    private var redirectToChat = mutableIntStateOf(-1)
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
        redirectToBookingDetail.intValue =
            bundleData.getInt(AppNavigator.Companion.ArgKey.BOOKING_ID, -1)
        redirectToChat.intValue =
            bundleData.getInt(AppNavigator.Companion.ArgKey.CONTACT_REQUEST_ID, -1)
    }

    @Composable
    override fun ProvideContent() {
        ScopeProvider(Scopes.App) {
            PermissionProvider {
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
                        val keyHomeTabArg = AppNavigator.Companion.ArgKey.HOME_TAB
                        composable(
                            "${Screen.Home.name}?$keyHomeTabArg={$keyHomeTabArg}",
                            arguments = listOf(navArgument(keyHomeTabArg) {
                                type = NavType.StringType
                                defaultValue = ""
                            })
                        ) { backStackEntry ->
                            val homeTabArg =
                                backStackEntry.arguments?.getString(keyHomeTabArg).orEmpty()
                            BackHandler { showExitAppDialog() }
                            HomeScreen(startTab = homeTabArg)
                        }
                        composable(Screen.SignIn.name) {
                            GuestSignInScreen(true)
                        }
                        composable(Screen.ManageStaffSignIn.name) {
                            ManageStaffSignInScreen(true)
                        }
                        composable(Screen.SignUp.name) {
                            GuestSignUpScreen()
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
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            val arg =
                                backStackEntry.arguments?.getString(keyBookingIDArg)
                                    ?: ""
                            DetailBookingScreen(arg)
                        }

                        val keyBookingArg =
                            AppNavigator.Companion.ArgKey.BOOKING_DTO
                        val keyLawyerIDArg =
                            AppNavigator.Companion.ArgKey.LAWYER_ID
                        val keyDancerIdArg =
                            AppNavigator.Companion.ArgKey.ID
                        val keyHasShowButtonsArg =
                            AppNavigator.Companion.ArgKey.HAS_SHOW_BUTTONS
                        composable(
                            "${Screen.DetailDancer.name}?$keyBookingArg={$keyBookingArg}&$keyLawyerIDArg={$keyLawyerIDArg}&$keyDancerIdArg={$keyDancerIdArg}&$keyHasShowButtonsArg={$keyHasShowButtonsArg}",
                            arguments = listOf(navArgument(keyBookingArg) {
                                type = NavType.StringType
                                defaultValue = ""
                            }, navArgument(keyLawyerIDArg) {
                                type = NavType.IntType
                                defaultValue = -1
                            }, navArgument(keyDancerIdArg) {
                                type = NavType.StringType
                                defaultValue = ""
                            }, navArgument(keyHasShowButtonsArg) {
                                type = NavType.BoolType
                                defaultValue = true
                            })
                        ) { backStackEntry ->
                            val dancerId =
                                backStackEntry.arguments?.getString(keyDancerIdArg) ?: ""
                            val hasShowButtons =
                                backStackEntry.arguments?.getBoolean(keyHasShowButtonsArg)
                                    ?: true
                            DetailDancerScreen(
                                dancerId = dancerId,
                                hasShowButtons = hasShowButtons
                            )
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

                        val keyClubIDArg =
                            AppNavigator.Companion.ArgKey.CLUB_ID
                        val keyHasNowArg =
                            AppNavigator.Companion.ArgKey.HAS_NOW
                        val keyRoomIDArg =
                            AppNavigator.Companion.ArgKey.ROOM_ID
                        val keyBookingClubIdArg = AppNavigator.Companion.ArgKey.CLUB_ID
                        composable(
                            "${Screen.Booking.name}?${AppNavigator.Companion.ArgKey.ID}={${AppNavigator.Companion.ArgKey.ID}}&$keyHasNowArg={$keyHasNowArg}&$keyBookingClubIdArg={$keyBookingClubIdArg}",
                            arguments = listOf(
                                navArgument(AppNavigator.Companion.ArgKey.ID) {
                                    type = NavType.StringType
                                    defaultValue = ""
                                },
                                navArgument(keyHasNowArg) {
                                    type = NavType.BoolType
                                    defaultValue = true
                                },
                                navArgument(keyBookingClubIdArg) {
                                    type = NavType.StringType
                                    defaultValue = ""
                                }
                            )
                        ) { backStackEntry ->
                            val dancerIdArg =
                                backStackEntry.arguments?.getString(AppNavigator.Companion.ArgKey.ID)
                                    .orEmpty()
                            val hasNowArg =
                                backStackEntry.arguments?.getBoolean(keyHasNowArg) ?: true
                            val clubIdArg =
                                backStackEntry.arguments?.getString(keyBookingClubIdArg)
                                    .orEmpty()
                            BookingScreen(
                                dancerId = dancerIdArg,
                                clubId = clubIdArg,
                                hasNow = hasNowArg,
                                navBackStackEntry = backStackEntry
                            )
                        }
                        val keyDancerIdsArg = AppNavigator.Companion.ArgKey.DANCER_IDS
                        val keyDancerNamesArg = AppNavigator.Companion.ArgKey.DANCER_NAMES
                        val keyDancerAvatarsArg = AppNavigator.Companion.ArgKey.DANCER_AVATARS
                        val keyClubNameArg = AppNavigator.Companion.ArgKey.CLUB_NAME
                        val keyClubImageArg = AppNavigator.Companion.ArgKey.CLUB_IMAGE
                        val keyBookingDateArg = AppNavigator.Companion.ArgKey.BOOKING_DATE
                        val keyBookingTimeArg = AppNavigator.Companion.ArgKey.BOOKING_TIME
                        val keyRoomNameArg = AppNavigator.Companion.ArgKey.ROOM_NAME
                        val keySongsArg = AppNavigator.Companion.ArgKey.SONGS
                        val keyGuestsArg = AppNavigator.Companion.ArgKey.GUESTS
                        val keyTotalAmountArg = AppNavigator.Companion.ArgKey.TOTAL_AMOUNT
                        composable(
                            "${Screen.BookingConfirm.name}?$keyDancerIdsArg={$keyDancerIdsArg}&$keyDancerNamesArg={$keyDancerNamesArg}&$keyDancerAvatarsArg={$keyDancerAvatarsArg}&$keyRoomIDArg={$keyRoomIDArg}&$keyClubNameArg={$keyClubNameArg}&$keyClubImageArg={$keyClubImageArg}&$keyBookingDateArg={$keyBookingDateArg}&$keyBookingTimeArg={$keyBookingTimeArg}&$keyRoomNameArg={$keyRoomNameArg}&$keySongsArg={$keySongsArg}&$keyGuestsArg={$keyGuestsArg}&$keyTotalAmountArg={$keyTotalAmountArg}&$keyHasNowArg={$keyHasNowArg}",
                            arguments = listOf(
                                navArgument(keyDancerIdsArg) {
                                    type = NavType.StringType
                                    defaultValue = ""
                                },
                                navArgument(keyDancerNamesArg) {
                                    type = NavType.StringType
                                    defaultValue = ""
                                },
                                navArgument(keyDancerAvatarsArg) {
                                    type = NavType.StringType
                                    defaultValue = ""
                                },
                                navArgument(keyRoomIDArg) {
                                    type = NavType.StringType
                                    defaultValue = ""
                                },
                                navArgument(keyClubNameArg) {
                                    type = NavType.StringType
                                    defaultValue = ""
                                },
                                navArgument(keyClubImageArg) {
                                    type = NavType.StringType
                                    defaultValue = ""
                                },
                                navArgument(keyBookingDateArg) {
                                    type = NavType.StringType
                                    defaultValue = ""
                                },
                                navArgument(keyBookingTimeArg) {
                                    type = NavType.StringType
                                    defaultValue = ""
                                },
                                navArgument(keyRoomNameArg) {
                                    type = NavType.StringType
                                    defaultValue = ""
                                },
                                navArgument(keySongsArg) {
                                    type = NavType.IntType
                                    defaultValue = 1
                                },
                                navArgument(keyGuestsArg) {
                                    type = NavType.IntType
                                    defaultValue = 1
                                },
                                navArgument(keyTotalAmountArg) {
                                    type = NavType.StringType
                                    defaultValue = "0"
                                },
                                navArgument(keyHasNowArg) {
                                    type = NavType.BoolType
                                    defaultValue = true
                                }
                            )
                        ) { backStackEntry ->
                            val dancerIdsArg =
                                backStackEntry.arguments?.getString(keyDancerIdsArg).orEmpty()
                            val dancerNamesArg =
                                backStackEntry.arguments?.getString(keyDancerNamesArg).orEmpty()
                            val dancerAvatarsArg =
                                backStackEntry.arguments?.getString(keyDancerAvatarsArg)
                                    .orEmpty()
                            val roomIdArg =
                                backStackEntry.arguments?.getString(keyRoomIDArg).orEmpty()
                            val clubNameArg =
                                backStackEntry.arguments?.getString(keyClubNameArg).orEmpty()
                            val clubImageArg =
                                backStackEntry.arguments?.getString(keyClubImageArg).orEmpty()
                            val bookingDateArg =
                                backStackEntry.arguments?.getString(keyBookingDateArg).orEmpty()
                            val bookingTimeArg =
                                backStackEntry.arguments?.getString(keyBookingTimeArg).orEmpty()
                            val roomNameArg =
                                backStackEntry.arguments?.getString(keyRoomNameArg).orEmpty()
                            val songsArg =
                                backStackEntry.arguments?.getInt(keySongsArg) ?: 1
                            val guestsArg =
                                backStackEntry.arguments?.getInt(keyGuestsArg) ?: 1
                            val totalAmountArg =
                                backStackEntry.arguments?.getString(keyTotalAmountArg).orEmpty()
                            val hasNowArg =
                                backStackEntry.arguments?.getBoolean(keyHasNowArg) ?: true
                            BookingConfirmScreen(
                                dancerIds = dancerIdsArg,
                                dancerNames = dancerNamesArg,
                                dancerAvatars = dancerAvatarsArg,
                                roomId = roomIdArg,
                                clubName = clubNameArg,
                                clubImage = clubImageArg,
                                bookingDate = bookingDateArg,
                                bookingTime = bookingTimeArg,
                                roomName = roomNameArg,
                                songs = songsArg,
                                guests = guestsArg,
                                totalAmount = totalAmountArg,
                                hasNow = hasNowArg
                            )
                        }

                        val keyPickForBookingArg =
                            AppNavigator.Companion.ArgKey.PICK_FOR_BOOKING
                        val keyExcludeDancerIdsArg =
                            AppNavigator.Companion.ArgKey.EXCLUDE_DANCER_IDS
                        composable(
                            "${Screen.DancerList.name}?$keyClubIDArg={$keyClubIDArg}&$keyPickForBookingArg={$keyPickForBookingArg}&$keyExcludeDancerIdsArg={$keyExcludeDancerIdsArg}",
                            arguments = listOf(
                                navArgument(keyClubIDArg) {
                                    type = NavType.StringType
                                    defaultValue = ""
                                },
                                navArgument(keyPickForBookingArg) {
                                    type = NavType.BoolType
                                    defaultValue = false
                                },
                                navArgument(keyExcludeDancerIdsArg) {
                                    type = NavType.StringType
                                    defaultValue = ""
                                }
                            )
                        ) { backStackEntry ->
                            val clubId =
                                backStackEntry.arguments?.getString(keyClubIDArg) ?: ""
                            val pickForBooking =
                                backStackEntry.arguments?.getBoolean(keyPickForBookingArg)
                                    ?: false
                            val excludeCsv =
                                backStackEntry.arguments?.getString(keyExcludeDancerIdsArg)
                                    .orEmpty()
                            val excludeIds = excludeCsv.split(',').map { it.trim() }
                                .filter { it.isNotEmpty() }.toSet()
                            DancerListScreen(
                                clubId = clubId,
                                pickForBooking = pickForBooking,
                                excludeDancerIds = excludeIds
                            )
                        }

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
                    LaunchedEffect(roomID) {
                        if (roomID > 0) {
                            appNavigator.navigateConversation(roomID)
                            redirectToChat.intValue = -1
                        }
                    }
                    LaunchedEffect(bookingID) {
                        if (bookingID > 0) {
                            appNavigator.navigateDetailCase(bookingID.toString())
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
                                appNavigator.navigateDetailCase(booking.contact_request_id.toString())
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