package com.kantek.dancer.booking.data.repo

import com.kantek.dancer.booking.data.local.UserLocalSource
import com.kantek.dancer.booking.domain.model.user.IAccount

class GetAccountRepo(private val userLocalSource: UserLocalSource) {
    suspend operator fun invoke(): IAccount {
        val account = userLocalSource.account.get()
        val password = userLocalSource.password.get()
        return object : IAccount {
            override val account: String get() = account
            override val password: String get() = password
        }
    }
}
