package com.kantek.dancer.booking.data.remote.api

import com.kantek.dancer.booking.app.AppConfig
import com.kantek.dancer.booking.data.helper.network.ApiAsync
import com.kantek.dancer.booking.data.helper.network.interceptor.NoTokenRequired
import com.kantek.dancer.booking.domain.model.response.UserDTO
import com.kantek.dancer.booking.domain.model.response.UserResponse
import com.kantek.dancer.booking.domain.model.ui.config.ContactForm
import com.kantek.dancer.booking.domain.model.ui.user.ChangePasswordForm
import com.kantek.dancer.booking.domain.model.ui.user.DeleteAccountForm
import com.kantek.dancer.booking.domain.model.ui.user.ResetPasswordForm
import com.kantek.dancer.booking.domain.model.ui.user.SignInForm
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface UserApi {

    @POST("user/login")
    fun signIn(@Body signInForm: SignInForm): ApiAsync<UserResponse>

    @POST("user/login-with")
    @Multipart
    fun signInGoogle(
        @PartMap buildMultipart: Map<String, @JvmSuppressWildcards RequestBody?>
    ): ApiAsync<UserResponse>

    @NoTokenRequired
    @POST("user/register")
    @Multipart
    fun signUp(
        @PartMap buildMultipart: Map<String, @JvmSuppressWildcards RequestBody?>,
        @Part avatarPart: MultipartBody.Part?
    ): ApiAsync<UserResponse>

    @POST("user/logout")
    fun logout(): ApiAsync<Any>

    @POST("user/verify-account")
    @FormUrlEncoded
    fun forgotPassword(
        @Field("account") account: String,
        @Field("type") type: String = "2"
    ): ApiAsync<String>

    @POST("user/change-language")
    @FormUrlEncoded
    fun changeLanguage(@Field("locale") locale: String): ApiAsync<Any>

    @POST("user/change-password")
    fun changePassword(@Body form: ChangePasswordForm): ApiAsync<Any>

    @POST("user/delete")
    fun delete(@Body form: DeleteAccountForm): ApiAsync<Any>

    @POST("user/update-profile")
    @Multipart
    fun updateProfile(
        @PartMap buildMultipart: Map<String, @JvmSuppressWildcards RequestBody?>,
        @Part avatarPart: MultipartBody.Part?
    ): ApiAsync<UserDTO>

    @POST("general/send-contact")
    fun contactUs(@Body form: ContactForm): ApiAsync<Any>

    @FormUrlEncoded
    @POST("user/verify-account")
    fun requestOTP(
        @Field("account") email: String,
        @Field("type") type: String = AppConfig.OTPType.FORGOT_PASSWORD
    ): ApiAsync<Any>

    @FormUrlEncoded
    @POST("user/check-verify-code")
    fun verifyOTP(
        @Field("account") email: String,
        @Field("code") code: String,
        @Field("mac_address") macAddress: String,
        @Field("type") type: String = AppConfig.OTPType.FORGOT_PASSWORD
    ): ApiAsync<Any>

    @POST("user/reset-password")
    fun resetPassword(@Body form: ResetPasswordForm): ApiAsync<Any>
}

