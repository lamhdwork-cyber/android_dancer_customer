package com.kantek.dancer.booking.domain.model.ui.user

import android.os.Build
import com.google.gson.annotations.SerializedName
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.domain.extension.isEmail
import com.kantek.dancer.booking.domain.extension.resourceError
import java.util.Locale
import kotlin.jvm.Transient

interface IAccount {
    val account: String
    val password: String
}

data class SignInForm(
    @SerializedName("email")
    override var account: String = "",
    override var password: String = "",
    @SerializedName("fcmToken")
    var deviceToken: String = ""
) : IAccount {
    fun validate() {
        if (account.isBlank()) resourceError(R.string.error_blank_email)
        if (password.isBlank()) resourceError(R.string.error_blank_password)
        if (password.length < 6) resourceError(R.string.error_valid_password)
    }
}

data class SignUpForm(
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    @Transient
    var phone: String = "",
    var password: String = "",
    @SerializedName("fcmToken")
    var deviceToken: String = ""
) {
    fun valid() {
        if (firstName.isBlank()) resourceError(R.string.error_blank_first_name)
        if (lastName.isBlank()) resourceError(R.string.error_blank_last_name)
        if (phone.isBlank() || phone.length != 10) resourceError(R.string.error_blank_phone)
        if (email.isBlank()) resourceError(R.string.error_blank_email)
        if (!email.isEmail()) resourceError(R.string.error_valid_email)
        if (password.isBlank()) resourceError(R.string.error_blank_password)
        if (password.length < 6) resourceError(R.string.error_valid_password)
    }
}

data class ChangePasswordForm(
    @SerializedName("current_password")
    var currentPassword: String = "",
    @SerializedName("new_password")
    var newPassword: String = "",
    @SerializedName("new_password_confirmation")
    var confirmNewPassword: String = ""
) {
    fun valid() {
        if (currentPassword.isBlank()) resourceError(R.string.error_blank_current_password)
        if (newPassword.length < 6) resourceError(R.string.error_valid_new_password)
        if (confirmNewPassword != newPassword) resourceError(R.string.error_valid_password_confirm)
    }
}

class DeleteAccountForm(
    @SerializedName("mac_address")
    var macAddress: String = "",
    val device: String = "${Build.MANUFACTURER.uppercase(Locale.getDefault())} ${
        Build.MODEL.uppercase(
            Locale.getDefault()
        )
    } -  Android ${Build.VERSION.RELEASE}"
)