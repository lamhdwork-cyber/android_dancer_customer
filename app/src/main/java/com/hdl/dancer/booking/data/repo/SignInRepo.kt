package com.hdl.dancer.booking.data.repo

import android.content.Context
import com.hdl.dancer.booking.R
import com.hdl.dancer.booking.app.AppConfig
import com.hdl.dancer.booking.app.ParameterInvalidException
import com.hdl.dancer.booking.data.local.UserLocalSource
import com.hdl.dancer.booking.data.remote.api.UserApi
import com.hdl.dancer.booking.data.model.response.UserDTO
import com.hdl.dancer.booking.domain.model.user.SignInForm

class SignInRepo(
    private val appContext: Context,
    private val userLocalSource: UserLocalSource,
    private val userApi: UserApi
) {
    suspend operator fun invoke(form: SignInForm): UserDTO? {
        val rs = userApi.signIn(
            form.copy(deviceToken = userLocalSource.getTokenPush())
        ).await()
        validateAppLoginRoleOrThrow(rs.user, appContext)
        userLocalSource.saveUserResponse(rs)
        return rs.user
    }

    internal fun validateAppLoginRoleOrThrow(user: UserDTO?, context: Context) {
        if (user != null && !AppConfig.UserRole.isAppLoginAllowed(user.role)) {
            throw ParameterInvalidException(
                context.getString(R.string.error_login_role_not_allowed)
            )
        }
    }
}
