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
import com.kantek.dancer.booking.data.helper.network.fatory.TLSSocketFactory
import com.kantek.dancer.booking.data.helper.network.interceptor.LanguageInterceptor
import com.kantek.dancer.booking.data.helper.network.interceptor.TokenInterceptor
import com.kantek.dancer.booking.data.local.FilterLocalSource
import com.kantek.dancer.booking.data.local.LanguageLocalSource
import com.kantek.dancer.booking.data.local.UserLocalSource
import com.kantek.dancer.booking.data.remote.api.BookingApi
import com.kantek.dancer.booking.data.remote.api.ConfigApi
import com.kantek.dancer.booking.data.remote.api.ConversationApi
import com.kantek.dancer.booking.data.remote.api.FAQsThreadsApi
import com.kantek.dancer.booking.data.remote.api.FilterApi
import com.kantek.dancer.booking.data.remote.api.LawyerApi
import com.kantek.dancer.booking.data.remote.api.NotificationApi
import com.kantek.dancer.booking.data.remote.api.UserApi
import com.kantek.dancer.booking.data.remote.socket.ChatSocketClient
import com.kantek.dancer.booking.data.remote.socket.SocketClient
import com.kantek.dancer.booking.data.repo.FetchAllBannerRepo
import com.kantek.dancer.booking.data.repo.FetchNotificationByPageRepoImpl
import com.kantek.dancer.booking.data.repo.GetAccountRepo
import com.kantek.dancer.booking.data.repo.GetLinkAboutUsRepo
import com.kantek.dancer.booking.data.repo.GetLinkTermsRepo
import com.kantek.dancer.booking.data.repo.GetStartDestinationMainRepo
import com.kantek.dancer.booking.data.repo.LanguageRepo
import com.kantek.dancer.booking.data.repo.NotificationRepo
import com.kantek.dancer.booking.data.repo.SignInRepo
import com.kantek.dancer.booking.data.repo.conversation.ChatRepo
import com.kantek.dancer.booking.data.repo.conversation.ChatSocketRepo
import com.kantek.dancer.booking.data.repo.conversation.FetchMessageByPageRepo
import com.kantek.dancer.booking.data.repo.conversation.UploadPhotosRepo
import com.kantek.dancer.booking.domain.extension.getBy
import com.kantek.dancer.booking.domain.factory.BookingFactory
import com.kantek.dancer.booking.domain.factory.ConfigFactory
import com.kantek.dancer.booking.domain.factory.ConversationFactory
import com.kantek.dancer.booking.domain.factory.FAQsThreadsFactory
import com.kantek.dancer.booking.domain.factory.FilterFactory
import com.kantek.dancer.booking.domain.factory.LanguageFactory
import com.kantek.dancer.booking.domain.factory.LawyerFactory
import com.kantek.dancer.booking.domain.factory.NotificationFactory
import com.kantek.dancer.booking.domain.factory.PhotoFactory
import com.kantek.dancer.booking.domain.factory.UserFactory
import com.kantek.dancer.booking.domain.formatter.TextFormatter
import com.kantek.dancer.booking.domain.formatter.TimeFormatter
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.domain.usecase.FetchNotificationCase
import com.kantek.dancer.booking.domain.usecase.GetStartDestinationCase
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
import com.kantek.dancer.booking.presentation.screen.cases.BookingCancelRepo
import com.kantek.dancer.booking.presentation.screen.cases.BookingRequestAgainRepo
import com.kantek.dancer.booking.presentation.screen.cases.DetailCasesVM
import com.kantek.dancer.booking.presentation.screen.cases.FetchBookingDetailRepo
import com.kantek.dancer.booking.presentation.screen.cases.FetchMyCaseByPageRepo
import com.kantek.dancer.booking.presentation.screen.cases.MyCasesVM
import com.kantek.dancer.booking.presentation.screen.conversation.ConversationVM
import com.kantek.dancer.booking.presentation.screen.faqs.FAQsThreadsVM
import com.kantek.dancer.booking.presentation.screen.faqs.FetchAnswerThreadsByPage
import com.kantek.dancer.booking.presentation.screen.faqs.FetchFAQsThreadsPagingRepo
import com.kantek.dancer.booking.presentation.screen.faqs.FetchQuestionThreadsByPage
import com.kantek.dancer.booking.presentation.screen.faqs.QuestionThreadsVM
import com.kantek.dancer.booking.presentation.screen.faqs.SubmitAnswerRepo
import com.kantek.dancer.booking.presentation.screen.faqs.SubmitQuestionRepo
import com.kantek.dancer.booking.presentation.screen.home.FetchFAQsPagingRepo
import com.kantek.dancer.booking.presentation.screen.home.HomeVM
import com.kantek.dancer.booking.presentation.screen.lawyer.DetailLawyerVM
import com.kantek.dancer.booking.presentation.screen.lawyer.FetchDetailLawyerRepo
import com.kantek.dancer.booking.presentation.screen.lawyer.GetLawyerDetailRepo
import com.kantek.dancer.booking.presentation.screen.review.CreateReviewRepo
import com.kantek.dancer.booking.presentation.screen.review.CreateReviewVM
import com.kantek.dancer.booking.presentation.screen.search.BookingCreateRepo
import com.kantek.dancer.booking.presentation.screen.search.FetchAllCityRepo
import com.kantek.dancer.booking.presentation.screen.search.FetchAllSpecialityRepo
import com.kantek.dancer.booking.presentation.screen.search.FetchAllStateRepo
import com.kantek.dancer.booking.presentation.screen.search.FetchLawyerListRepo
import com.kantek.dancer.booking.presentation.screen.search.FilterCurrentRepo
import com.kantek.dancer.booking.presentation.screen.search.LawyerListVM
import com.kantek.dancer.booking.presentation.screen.search.QuickRequestVM
import com.kantek.dancer.booking.presentation.viewmodel.AccountVM
import com.kantek.dancer.booking.presentation.viewmodel.BrowserVM
import com.kantek.dancer.booking.presentation.viewmodel.DeleteAccountRepo
import com.kantek.dancer.booking.presentation.viewmodel.FetchReviewByPageRepo
import com.kantek.dancer.booking.presentation.viewmodel.FetchUserRepo
import com.kantek.dancer.booking.presentation.viewmodel.LanguageVM
import com.kantek.dancer.booking.presentation.viewmodel.MainVM
import com.kantek.dancer.booking.presentation.viewmodel.NotificationVM
import com.kantek.dancer.booking.presentation.viewmodel.ReviewVM
import com.kantek.dancer.booking.presentation.viewmodel.SignInGoogleRepo
import com.kantek.dancer.booking.presentation.viewmodel.SignInVM
import com.kantek.dancer.booking.presentation.viewmodel.SignOutRepo
import kotlinx.coroutines.CoroutineScope
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
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
        val tlsSocketFactory = TLSSocketFactory()
        OkHttpClient.Builder()
            .sslSocketFactory(tlsSocketFactory, tlsSocketFactory.systemDefaultTrustManager())
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
    single { provideApi<LawyerApi>(get()) }
    single { provideApi<FilterApi>(get()) }
    single { provideApi<FAQsThreadsApi>(get()) }
}

val presentationModule = module {
    single(createdAtStart = true) { ActivityRetriever(get<Application>()) }
    single { AppKeyboard(get()) }
    scope<Any> {
        scoped { AppNavigator() }
        scoped { AppPopup() }
        scoped<CoroutineScope> { ScopedCoroutineScope() }
    }
    viewModel { NotificationVM(get()) }
    viewModel { LanguageVM(get()) }
    viewModel { MainVM(get()) }
    viewModel { AccountVM(get(), get(), get()) }
    viewModel { SignInVM(get(), get(), get(), get()) }
    viewModel { SignUpVM(get(), get()) }
    viewModel { ForgotPasswordVM(get(), get(), get()) }
    viewModel { HomeVM(get(), get()) }
    viewModel { MyCasesVM(get(), get(), get()) }
    viewModel { QuickRequestVM(get(), get(), get()) }
    viewModel { DetailCasesVM(get(), get(), get(), get(), get()) }
    viewModel { DetailLawyerVM(get(), get()) }
    viewModel { ChangePasswordVM(get(), getBy(Scopes.App)) }
    viewModel { MyProfileVM(get(), get(), getBy(Scopes.App)) }
    viewModel { ContactUsVM(get(), get(), getBy(Scopes.App)) }
    viewModel { BrowserVM(get(), get()) }
    viewModel { OTPVerifyVM(get(), get()) }
    viewModel { RetPasswordVM(get(), getBy(Scopes.App)) }
    viewModel { LawyerListVM(get(), get(), get(), get(), get(), get()) }
    viewModel { ReviewVM(get()) }
    viewModel { CreateReviewVM(get(), get(), get()) }
    viewModel { FAQsThreadsVM(get()) }
    viewModel { QuestionThreadsVM(get(), get(), get(),get()) }
}

val dataModule = module {
    single { get<Application>().applicationContext }
    single { AppEvent() }
    single { AppNotifications(get()) }
    single { SaveStateHandler() }
    single { UserLocalSource(get(), get(), get()) }
    single { TokenInterceptor(get()) }
    single { LanguageInterceptor(get()) }
    single { ShareIOScope() }
    single<NotificationRepo> { FetchNotificationByPageRepoImpl(get()) }
    factory { LanguageRepo(get(), get(), get(), get()) }
    single { LanguageLocalSource(get()) }
    factory { GetStartDestinationMainRepo(get(), get()) }
    factory { FetchUserRepo(get(), get(), get()) }
    factory { SignOutRepo(get(), get()) }
    factory { GetAccountRepo(get()) }
    factory { SignInRepo(get(), get(), get()) }
    factory { SignUpRepo(get(), get(), get()) }
    factory { ForgotPasswordRepo(get()) }
    factory { FetchAllBannerRepo(get(), get()) }
    factory { FetchFAQsPagingRepo(get(), get()) }
    factory { BookingCreateRepo(get(), get(), get()) }
    factory { FetchMyCaseByPageRepo(get(), get()) }
    factory { BookingRequestAgainRepo(get()) }
    factory { BookingCancelRepo(get()) }
    factory { FetchBookingDetailRepo(get(), get()) }
    factory { GetLawyerDetailRepo(get()) }
    factory { ChangePasswordRepo(get()) }
    factory { DeleteAccountRepo(get(), get(), get()) }
    factory { UpdateProfileRepo(get(), get(), get()) }
    factory { FetchSettingRepo(get(), get()) }
    factory { ContactUsRepo(get()) }
    factory { SignInGoogleRepo(get(), get(), get()) }
    factory { GetLinkAboutUsRepo(get()) }
    factory { GetLinkTermsRepo(get()) }
    factory { RequestOTPRepo(get()) }
    factory { VerifyOTPRepo(get(), get()) }
    factory { ResetPasswordRepo(get()) }
    factory { FetchAllStateRepo(get(), get()) }
    factory { FetchLawyerListRepo(get(), get(), get()) }
    factory { FetchDetailLawyerRepo(get(), get()) }
    factory { FetchReviewByPageRepo(get(), get()) }
    single { FilterLocalSource(get()) }
    factory { FilterCurrentRepo(get()) }
    factory { FetchAllCityRepo(get(), get()) }
    factory { FetchAllSpecialityRepo(get(), get()) }
    factory { CreateReviewRepo(get()) }
    factory { FetchFAQsThreadsPagingRepo(get(), get()) }
    factory { FetchQuestionThreadsByPage(get(), get()) }
    factory { SubmitQuestionRepo(get()) }
    factory { SubmitAnswerRepo(get()) }
    factory { FetchAnswerThreadsByPage(get(), get()) }
}

val domainModule = module {
    factory { FetchNotificationCase(get(), get()) }
    factory { GetStartDestinationCase(get()) }
    single { TextFormatter() }
    single { NotificationFactory(get()) }
    single { LanguageFactory() }
    single { UserFactory(get()) }
    single { PhotoFactory(get()) }
    single { ConfigFactory(get()) }
    single { BookingFactory(get(), get()) }
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
    viewModel { ConversationVM(get(), get(), get(), get(), get()) }
    single { provideApi<ConversationApi>(get()) }
}