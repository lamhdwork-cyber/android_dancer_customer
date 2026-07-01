package com.kantek.dancer.booking.domain.provider

fun interface CurrentUserRoleProvider {
    suspend fun getRole(): String?
}
