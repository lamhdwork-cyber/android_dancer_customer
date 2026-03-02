package com.kantek.dancer.booking.data.repo

import android.support.core.extensions.safe
import com.kantek.dancer.booking.data.local.UserLocalSource
import com.kantek.dancer.booking.domain.model.ui.user.IAccount

class GetAccountRepo(private val userLocalSource: UserLocalSource) {
    operator fun invoke(): IAccount {
        return object : IAccount {
            override val account: String
                get() = userLocalSource.account.safe()
            override val password: String
                get() = userLocalSource.password.safe()
        }
    }
}