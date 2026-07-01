package com.kantek.dancer.booking.data.local

import android.content.Context
import android.support.persistent.cache.datastore.GsonDataStoreCaching
import android.support.core.helper.ShareIOScope
import com.kantek.dancer.booking.data.model.response.UserDTO
import com.kantek.dancer.booking.data.model.response.UserResponse
import kotlinx.coroutines.flow.map

class UserLocalSource(
    context: Context,
    private val shareIOScope: ShareIOScope,
    private val languageLocalSource: LanguageLocalSource
) {
    private val caching = GsonDataStoreCaching(context)

    val account = caching.string("auth:account", "")
    val password = caching.string("auth:password", "")
    val refreshToken = caching.string("token:refresh", "")
    val apiToken = caching.string("token:api", "")
    private val pushToken = caching.string("token:push", "")
    private val chatRoomId = caching.int("chat:room:id", 0)
    private val user = caching.reference<UserDTO>(UserDTO::class.java.name)

    suspend fun getUserDto(): UserDTO? = user.get()
    suspend fun isLogin() = user.get() != null
    suspend fun getToken(): String = apiToken.get()
    fun getUserLive() = user.asFlow()

    suspend fun saveUser(userDTO: UserDTO?) = user.set(userDTO)

    suspend fun saveUserResponse(it: UserResponse?) {
        saveUser(it?.user)
        val access = it?.tokens?.accessToken
        if (!access.isNullOrEmpty()) apiToken.set(access)
        val refresh = it?.tokens?.refreshToken
        if (!refresh.isNullOrEmpty()) refreshToken.set(refresh)
        languageLocalSource.save(it?.user?.language ?: "")
    }

    suspend fun logout() {
        saveUser(null)
        apiToken.set("")
        refreshToken.set("")
    }

    suspend fun saveTokenPush(token: String) = pushToken.set(token)
    suspend fun getTokenPush(): String = pushToken.get()

    suspend fun setChatRoomIDCurrent(bookingID: Int) = chatRoomId.set(bookingID)
    suspend fun getChatRoomIDCurrent(): Int = chatRoomId.get()
}
