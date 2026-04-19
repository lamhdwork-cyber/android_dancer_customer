package com.kantek.dancer.booking.data.repo

import com.kantek.dancer.booking.data.local.UserLocalSource
import com.kantek.dancer.booking.data.remote.api.UserApi
import com.kantek.dancer.booking.domain.model.response.UserDTO
import com.kantek.dancer.booking.domain.model.ui.user.SignInForm

class SignInRepo(
    private val userLocalSource: UserLocalSource,
    private val userApi: UserApi
) {
    suspend operator fun invoke(form: SignInForm): UserDTO? {
        val rs = userApi.signIn(
            form.copy(deviceToken = userLocalSource.getTokenPush())
        ).await()
        userLocalSource.saveUserResponse(rs)
        return rs.user
    }
}

