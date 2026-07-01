package com.kantek.dancer.booking.data.local

import com.kantek.dancer.booking.domain.provider.CurrentUserRoleProvider

class UserRoleProvider(
    private val userLocalSource: UserLocalSource
) : CurrentUserRoleProvider {
    override suspend fun getRole(): String? = userLocalSource.getUserDto()?.role
}
