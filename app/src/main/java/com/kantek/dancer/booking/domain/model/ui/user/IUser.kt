package com.kantek.dancer.booking.domain.model.ui.user

import android.net.Uri
import androidx.annotation.StringRes
import com.google.gson.annotations.SerializedName
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.domain.extension.isEmail
import com.kantek.dancer.booking.domain.extension.resourceError
import com.kantek.dancer.booking.domain.model.response.lawyer.LawyerDTO

interface IUser {
    val id: Int get() = 0
    val firstName get() = ""
    val lastName get() = ""
    val phoneNumber get() = ""
    val phoneDisplay get() = ""
    val fullName get() = ""
    val email get() = ""
    val languageRes @StringRes get() = 0
    val avatarURL get() = ""
}

interface ILawyer : IUser {
    val exp get() = ""
    val cases get() = ""
    val specialties: List<String>? get() = null
    val rating: Float get() = 0f
    val reviewCount: Int get() = 0
    val owner: LawyerDTO? get() = null
}

interface ILawyerDetail : ILawyer {
    val education get() = ""
    val licenseURL get() = ""
    val achievements get() = ""
}

data class ProfileForm(
    var avatarPath: String = "",
    var avatarUri: Uri? = null,
    var firstName: String = "",
    var lastname: String = "",
    var email: String = "",
    var phone: String = ""
) {
    fun valid() {
        if (firstName.isBlank()) resourceError(R.string.error_blank_first_name)
        if (lastname.isBlank()) resourceError(R.string.error_blank_last_name)
        if (email.isBlank()) resourceError(R.string.error_blank_email)
        if (!email.isEmail()) resourceError(R.string.error_valid_email)
        if (phone.isBlank() || phone.length != 10) resourceError(R.string.error_blank_phone)
    }
}

data class ResetPasswordForm(
    @SerializedName("account")
    override var email: String = "",
    @SerializedName("new_password")
    var newPassword: String = "",
    @SerializedName("new_password_confirmation")
    var passwordConfirm: String = "",
) : IUser {
    fun valid(email: String) {
        if (newPassword.isBlank()) resourceError(R.string.error_blank_password)
        if (newPassword.length < 6) resourceError(R.string.error_valid_password)
        if (passwordConfirm != newPassword) resourceError(R.string.error_valid_password_confirm)
        this.email = email
    }
}