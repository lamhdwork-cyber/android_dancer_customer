package com.kantek.dancer.booking.data.local

import android.content.Context
import android.support.persistent.cache.GsonCaching
import com.kantek.dancer.booking.data.helper.ShareIOScope
import com.kantek.dancer.booking.domain.model.response.UserDTO
import com.kantek.dancer.booking.domain.model.response.UserResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UserLocalSource(
    context: Context,
    private val shareIOScope: ShareIOScope,
    private val languageLocalSource: LanguageLocalSource
) {
    private val caching = GsonCaching(context)
    private val userLive = MutableStateFlow(false)

    var account: String by caching.string("auth:account", "")
    var password: String by caching.string("auth:password", "")
    var apiToken: String by caching.string("token:api", "")
    private var chatIDCurrent: Int by caching.int("chat:room:id", 0)
    private var pushToken: String by caching.string("token:push", "")
    private var user: UserDTO? by caching.reference(UserDTO::class.java.name)

    fun saveUserResponse(it: UserResponse?) {
        saveUser(it?.user)
        if (!it?.token.isNullOrEmpty()) saveToken(it?.token!!)
        languageLocalSource.save(it?.user?.language)
    }

    fun saveUser(userDTO: UserDTO?) {
        user = userDTO
        postLive()
    }

    private fun saveToken(apiToken: String) {
        this.apiToken = apiToken
    }

    fun getUserDto(): UserDTO? = user

    fun isLogin() = user != null

    fun logout() {
        saveUser(null)
        saveToken("")
    }

    fun saveTokenPush(pushToken: String) {
        this.pushToken = pushToken
    }

    fun getTokenPush(): String {
        return pushToken
    }

    fun getUserLive() = userLive

    fun postLive() {
        shareIOScope.launch { userLive.emit(!userLive.value) }
    }

    fun getToken(): String = apiToken

    fun setChatRoomIDCurrent(bookingID: Int) {
        chatIDCurrent = bookingID
    }

    fun getChatRoomIDCurrent() = chatIDCurrent
}