package com.kantek.dancer.booking.domain.provider

fun interface CurrentUserRoleProvider {
    fun getRole(): String?
}
