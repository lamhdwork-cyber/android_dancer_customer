package com.kantek.dancer.booking.app

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kantek.dancer.booking.data.event.AppEvent
import com.kantek.dancer.booking.data.extensions.provideApi
import com.kantek.dancer.booking.data.helper.SaveStateHandler
import com.kantek.dancer.booking.data.helper.ShareIOScope
import com.kantek.dancer.booking.data.helper.network.ApiAsyncAdapterFactory
import com.kantek.dancer.booking.data.helper.network.DefaultApiErrorHandler
import com.kantek.dancer.booking.data.helper.network.interceptor.LanguageInterceptor
import com.kantek.dancer.booking.data.helper.network.interceptor.TokenInterceptor
import com.kantek.dancer.booking.data.local.FilterLocalSource
import com.kantek.dancer.booking.data.local.LanguageLocalSource
import com.kantek.dancer.booking.data.local.UserLocalSource
import com.kantek.dancer.booking.data.local.UserRoleProvider
import com.kantek.dancer.booking.data.remote.api.BookingApi
import com.kantek.dancer.booking.data.remote.api.ClubApi
import com.kantek.dancer.booking.data.remote.api.ConfigApi
import com.kantek.dancer.booking.data.remote.api.ConversationApi
import com.kantek.dancer.booking.data.remote.api.DancerApi
import com.kantek.dancer.booking.data.remote.api.FAQsThreadsApi
import com.kantek.dancer.booking.data.remote.api.FilterApi
import com.kantek.dancer.booking.data.remote.api.LawyerApi
import com.kantek.dancer.booking.data.remote.api.NotificationApi
import com.kantek.dancer.booking.data.remote.api.RoomApi
import com.kantek.dancer.booking.data.remote.api.UserApi
import com.kantek.dancer.booking.data.remote.socket.ChatSocketClient
import com.kantek.dancer.booking.data.remote.socket.SocketClient
import com.kantek.dancer.booking.data.repo.ClubRepoImpl
import com.kantek.dancer.booking.data.repo.DancerRepoImpl
import com.kantek.dancer.booking.data.repo.FetchAllBannerRepo
import com.kantek.dancer.booking.data.repo.RoomRepoImpl
import com.kantek.dancer.booking.data.repo.DestinationRepoImpl
import com.kantek.dancer.booking.domain.repo.ClubRepo
import com.kantek.dancer.booking.domain.repo.DancerRepo
import com.kantek.dancer.booking.domain.repo.RoomRepo
import com.kantek.dancer.booking.domain.repo.DestinationRepo
import com.kantek.dancer.booking.data.repo.GetAccountRepo
import com.kantek.dancer.booking.data.repo.GetLinkAboutUsRepo
import com.kantek.dancer.booking.data.repo.GetLinkTermsRepo
import com.kantek.dancer.booking.data.repo.LanguageRepo
import com.kantek.dancer.booking.domain.repo.NotificationRepo
import com.kantek.dancer.booking.data.repo.NotificationRepoImpl
import com.kantek.dancer.booking.data.repo.SignInRepo
import com.kantek.dancer.booking.data.repo.conversation.ChatRepo
import com.kantek.dancer.booking.data.repo.conversation.ChatSocketRepo
import com.kantek.dancer.booking.data.repo.conversation.FetchMessageByPageRepo
import com.kantek.dancer.booking.data.repo.conversation.UploadPhotosRepo
import com.kantek.dancer.booking.data.extension.getBy
import com.kantek.dancer.booking.data.factory.BookingFactory
import com.kantek.dancer.booking.data.factory.ClubFactory
import com.kantek.dancer.booking.data.factory.ConfigFactory
import com.kantek.dancer.booking.data.factory.ConversationFactory
import com.kantek.dancer.booking.data.factory.DancerFactory
import com.kantek.dancer.booking.data.factory.FAQsThreadsFactory
import com.kantek.dancer.booking.data.factory.FilterFactory
import com.kantek.dancer.booking.data.factory.IntroduceFactory
import com.kantek.dancer.booking.data.factory.LanguageFactory
import com.kantek.dancer.booking.data.factory.LawyerFactory
import com.kantek.dancer.booking.data.factory.NotificationFactory
import com.kantek.dancer.booking.data.factory.PhotoFactory
import com.kantek.dancer.booking.data.factory.RoomFactory
import com.kantek.dancer.booking.data.factory.UserFactory
import com.kantek.dancer.booking.data.formatter.TextFormatter
import com.kantek.dancer.booking.data.formatter.TimeFormatter
import com.kantek.dancer.booking.presentation.model.support.Scopes
import com.kantek.dancer.booking.domain.provider.CurrentUserRoleProvider
import com.kantek.dancer.booking.domain.usecase.FetchClubCase
import com.kantek.dancer.booking.domain.usecase.FetchClubDancersAdminPageCase
import com.kantek.dancer.booking.domain.usecase.FetchDancerByClubCase
import com.kantek.dancer.booking.domain.usecase.FetchDancerDetailCase
import com.kantek.dancer.booking.domain.usecase.FetchRoomsByClubCase
import com.kantek.dancer.booking.domain.usecase.GetStartDestinationCase
import com.kantek.dancer.booking.domain.usecase.NotificationUseCase
import com.kantek.dancer.booking.presentation.helper.ActivityRetriever
import com.kantek.dancer.booking.presentation.helper.AppKeyboard
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.helper.AppPopup
import com.kantek.dancer.booking.presentation.helper.ScopedCoroutineScope
import com.kantek.dancer.booking.presentation.screen.account.ContactUsRepo
import com.kantek.dancer.booking.presentation.screen.account.ContactUsVM
import com.kantek.dancer.booking.presentation.screen.account.FetchSettingRepo
import com.kantek.dancer.booking.presentation.screen.account.MyProfileVM
import com.kantek.dancer.booking.presentation.screen.account.UpdateProfileRepo
import com.kantek.dancer.booking.presentation.screen.auth.ChangePasswordRepo
import com.kantek.dancer.booking.presentation.screen.auth.ChangePasswordVM
import com.kantek.dancer.booking.presentation.screen.auth.SignUpRepo
import com.kantek.dancer.booking.presentation.screen.auth.SignUpVM
import com.kantek.dancer.booking.presentation.screen.auth.forgot.ForgotPasswordRepo
import com.kantek.dancer.booking.presentation.screen.auth.forgot.ForgotPasswordVM
import com.kantek.dancer.booking.presentation.screen.auth.forgot.RequestOTPRepo
import com.kantek.dancer.booking.presentation.screen.auth.forgot.ResetPasswordRepo
import com.kantek.dancer.booking.presentation.screen.auth.forgot.RetPasswordVM
import com.kantek.dancer.booking.presentation.screen.auth.otp.OTPVerifyVM
import com.kantek.dancer.booking.presentation.screen.auth.otp.VerifyOTPRepo
import com.kantek.dancer.booking.presentation.screen.booking.BookingWaitRepo
import com.kantek.dancer.booking.presentation.screen.booking.BookingCancelRepo
import com.kantek.dancer.booking.presentation.screen.booking.BookingReadyRepo
import com.kantek.dancer.booking.presentation.screen.booking.BookingConfirmRepo
import com.kantek.dancer.booking.presentation.screen.booking.BookingConfirmVM
import com.kantek.dancer.booking.presentation.screen.booking.BookingRequestAgainRepo
import com.kantek.dancer.booking.presentation.screen.booking.BookingVM
import com.kantek.dancer.booking.presentation.screen.booking.DetailBookingVM
import com.kantek.dancer.booking.presentation.screen.booking.FetchBookingDetailRepo
import com.kantek.dancer.booking.presentation.screen.booking.FetchMyBookingByPageRepo
import com.kantek.dancer.booking.presentation.screen.booking.MyBookingVM
import com.kantek.dancer.booking.presentation.screen.conversation.ConversationVM
import com.kantek.dancer.booking.presentation.screen.dancer.DancerListOfAdminVM
import com.kantek.dancer.booking.presentation.screen.dancer.DancerListVM
import com.kantek.dancer.booking.presentation.screen.dancer.DetailDancerVM
import com.kantek.dancer.booking.presentation.screen.faqs.FAQsThreadsVM
import com.kantek.dancer.booking.presentation.screen.faqs.FetchAnswerThreadsByPage
import com.kantek.dancer.booking.presentation.screen.faqs.FetchFAQsThreadsPagingRepo
import com.kantek.dancer.booking.presentation.screen.faqs.FetchQuestionThreadsByPage
import com.kantek.dancer.booking.presentation.screen.faqs.QuestionThreadsVM
import com.kantek.dancer.booking.presentation.screen.faqs.SubmitAnswerRepo
import com.kantek.dancer.booking.presentation.screen.faqs.SubmitQuestionRepo
import com.kantek.dancer.booking.presentation.screen.home.FetchFAQsPagingRepo
import com.kantek.dancer.booking.presentation.screen.home.HomeVM
import com.kantek.dancer.booking.presentation.screen.introduce.FetchIntroduceRepo
import com.kantek.dancer.booking.presentation.screen.introduce.IntroduceVM
import com.kantek.dancer.booking.presentation.screen.review.CreateReviewRepo
import com.kantek.dancer.booking.presentation.screen.review.CreateReviewVM
import com.kantek.dancer.booking.presentation.viewmodel.AccountVM
import com.kantek.dancer.booking.presentation.viewmodel.BrowserVM
import com.kantek.dancer.booking.presentation.viewmodel.DeleteAccountRepo
import com.kantek.dancer.booking.presentation.viewmodel.FetchReviewByPageRepo
import com.kantek.dancer.booking.presentation.viewmodel.FetchUserRepo
import com.kantek.dancer.booking.presentation.viewmodel.FindClubVM
import com.kantek.dancer.booking.presentation.viewmodel.LanguageVM
import com.kantek.dancer.booking.presentation.viewmodel.MainVM
import com.kantek.dancer.booking.presentation.viewmodel.ManageStaffSignInVM
import com.kantek.dancer.booking.presentation.viewmodel.NotificationVM
import com.kantek.dancer.booking.presentation.viewmodel.ReviewVM
import com.kantek.dancer.booking.presentation.viewmodel.SignInGoogleRepo
import com.kantek.dancer.booking.presentation.viewmodel.SignInVM
import com.kantek.dancer.booking.presentation.viewmodel.SignOutRepo
import kotlinx.coroutines.CoroutineScope
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.UUID
import java.util.concurrent.TimeUnit

val serializeModule = module {
    single { Gson() }
    single<Converter.Factory> {
        GsonConverterFactory.create(
            GsonBuilder().create()
        )
    }
}

val networkModule = module {
    single {
        val application: Application = get()
        val cacheDir = File(application.cacheDir, UUID.randomUUID().toString())
        val cache = Cache(cacheDir, 10485760L) // 10mb
        OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(get<TokenInterceptor>())
            .addInterceptor(get<LanguageInterceptor>())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    factory<Retrofit.Builder> {
        Retrofit.Builder()
            .addConverterFactory(get())
            .client(get())
    }
}

val apiModule = module {
    single {
        get<Retrofit.Builder>()
            .addConverterFactory(get())
            .client(get())
            .baseUrl(AppConfig.END_POINT)
            .addCallAdapterFactory(ApiAsyncAdapterFactory(DefaultApiErrorHandler()))
            .build()
    }

    single { provideApi<UserApi>(get()) }
    single { provideApi<NotificationApi>(get()) }
    single { provideApi<ConfigApi>(get()) }
    single { provideApi<BookingApi>(get()) }
    single { provideApi<ClubApi>(get()) }
    single { provideApi<LawyerApi>(get()) }
    single { provideApi<DancerApi>(get()) }
    single { provideApi<RoomApi>(get()) }
    single { provideApi<FilterApi>(get()) }
    single { provideApi<FAQsThreadsApi>(get()) }
}

val presentationModule = module {
    single(createdAtStart = true) { ActivityRetriever(get<Application>()) }
    single { AppKeyboard(get()) }
    single { AppNavigator() }
    scope<Any> {
        scoped { AppPopup() }
        scoped<CoroutineScope> { ScopedCoroutineScope() }
    }
    viewModelOf(::NotificationVM)
    viewModelOf(::FindClubVM)
    viewModelOf(::LanguageVM)
    viewModelOf(::MainVM)
    viewModelOf(::AccountVM)
    viewModelOf(::SignInVM)
    viewModelOf(::ManageStaffSignInVM)
    viewModelOf(::SignUpVM)
    viewModelOf(::ForgotPasswordVM)
    viewModelOf(::IntroduceVM)
    viewModelOf(::HomeVM)
    viewModelOf(::MyBookingVM)
    viewModelOf(::DetailBookingVM)
    viewModelOf(::DetailDancerVM)
    viewModel { ChangePasswordVM(get(), getBy(Scopes.App)) }
    viewModel { MyProfileVM(get(), get(), getBy(Scopes.App)) }
    viewModel { ContactUsVM(get(), get(), getBy(Scopes.App)) }
    viewModelOf(::BrowserVM)
    viewModelOf(::OTPVerifyVM)
    viewModel { RetPasswordVM(get(), getBy(Scopes.App)) }
    viewModelOf(::DancerListVM)
    viewModelOf(::DancerListOfAdminVM)
    viewModelOf(::BookingVM)
    viewModelOf(::BookingConfirmVM)
    viewModelOf(::ReviewVM)
    viewModelOf(::CreateReviewVM)
    viewModelOf(::FAQsThreadsVM)
    viewModelOf(::QuestionThreadsVM)
}

val dataModule = module {
    single { get<Application>().applicationContext }
    single { AppEvent() }
    single { AppNotifications(get()) }
    single { SaveStateHandler() }
    single { UserLocalSource(get(), get(), get()) }
    single<CurrentUserRoleProvider> { UserRoleProvider(get()) }
    single { TokenInterceptor(get()) }
    single { LanguageInterceptor(get()) }
    single { ShareIOScope() }
    single<NotificationRepo> { NotificationRepoImpl(get(), get()) }
    single<ClubRepo> { ClubRepoImpl(get(), get()) }
    single<DancerRepo> { DancerRepoImpl(get(), get()) }
    single<RoomRepo> { RoomRepoImpl(get(), get()) }
    single<DestinationRepo> { DestinationRepoImpl(get(), get()) }
    factory { LanguageRepo(get(), get(), get(), get()) }
    single { LanguageLocalSource(get()) }
    factory { FetchUserRepo(get(), get(), get()) }
    factory { SignOutRepo(get(), get()) }
    factory { GetAccountRepo(get()) }
    factory { SignInRepo(get<Application>().applicationContext, get(), get()) }
    factory { SignUpRepo(get(), get(), get()) }
    factory { ForgotPasswordRepo(get()) }
    factory { FetchIntroduceRepo(get()) }
    factory { FetchAllBannerRepo(get(), get()) }
    factory { FetchFAQsPagingRepo(get(), get()) }
    factory { BookingConfirmRepo(get()) }
    factory { FetchMyBookingByPageRepo(get(), get()) }
    factory { BookingRequestAgainRepo(get()) }
    factory { BookingCancelRepo(get()) }
    factory { BookingWaitRepo(get()) }
    factory { BookingReadyRepo(get()) }
    factory { FetchBookingDetailRepo(get(), get()) }
    factory { ChangePasswordRepo(get()) }
    factory { DeleteAccountRepo(get(), get()) }
    factory { UpdateProfileRepo(get(), get(), get(),get()) }
    factory { FetchSettingRepo(get(), get()) }
    factory { ContactUsRepo(get()) }
    factory { SignInGoogleRepo(get(), get(), get()) }
    factory { GetLinkAboutUsRepo(get()) }
    factory { GetLinkTermsRepo(get()) }
    factory { RequestOTPRepo(get()) }
    factory { VerifyOTPRepo(get(), get()) }
    factory { ResetPasswordRepo(get()) }
    factory { FetchReviewByPageRepo(get(), get()) }
    single { FilterLocalSource(get()) }
    factory { CreateReviewRepo(get()) }
    factory { FetchFAQsThreadsPagingRepo(get(), get()) }
    factory { FetchQuestionThreadsByPage(get(), get()) }
    factory { SubmitQuestionRepo(get()) }
    factory { SubmitAnswerRepo(get()) }
    factory { FetchAnswerThreadsByPage(get(), get()) }
}

val domainModule = module {
    factory { NotificationUseCase(get()) }
    factory { FetchClubCase(get()) }
    factory { FetchDancerByClubCase(get()) }
    factory { FetchClubDancersAdminPageCase(get()) }
    factory { FetchDancerDetailCase(get()) }
    factory { FetchRoomsByClubCase(get()) }
    factory { GetStartDestinationCase(get()) }
    single { TextFormatter() }
    single { NotificationFactory(get()) }
    single { LanguageFactory() }
    single { IntroduceFactory() }
    single { UserFactory(get()) }
    single { PhotoFactory(get()) }
    single { ConfigFactory(get()) }
    single { BookingFactory(get(), get(), get(), get()) }
    single { RoomFactory(get()) }
    single { ClubFactory() }
    single { DancerFactory() }
    single { LawyerFactory(get()) }
    single { FilterFactory() }
    single { FAQsThreadsFactory(get()) }
}

val conversation = module {
    single { TimeFormatter() }
    single { SocketClient() }
    single { ChatSocketClient(get(), get()) }
    factory { ConversationFactory(get(), get()) }
    factory { ChatSocketRepo(get(), get()) }
    factory { ChatRepo(get()) }
    factory { FetchMessageByPageRepo(get(), get()) }
    factory { UploadPhotosRepo(get(), get()) }
    viewModelOf(::ConversationVM)
    single { provideApi<ConversationApi>(get()) }
}