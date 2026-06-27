package com.hdl.dancer.booking.domain.provider

fun interface CurrentUserRoleProvider {
    fun getRole(): String?
}
