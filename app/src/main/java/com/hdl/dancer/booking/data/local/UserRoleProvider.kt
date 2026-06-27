package com.hdl.dancer.booking.data.local

import com.hdl.dancer.booking.domain.provider.CurrentUserRoleProvider

class UserRoleProvider(
    private val userLocalSource: UserLocalSource
) : CurrentUserRoleProvider {
    override fun getRole(): String? = userLocalSource.getUserDto()?.role
}
