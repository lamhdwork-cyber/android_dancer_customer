package com.kantek.dancer.booking.presentation.viewmodel

import android.content.Context
import com.kantek.dancer.booking.app.AppNotifications
import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.data.extensions.getDeviceID
import com.kantek.dancer.booking.data.local.UserLocalSource
import com.kantek.dancer.booking.data.remote.api.UserApi
import com.kantek.dancer.booking.data.repo.LanguageRepo
import com.kantek.dancer.booking.domain.factory.UserFactory
import com.kantek.dancer.booking.domain.model.ui.user.DeleteAccountForm
import com.kantek.dancer.booking.domain.model.ui.user.IUser
import com.kantek.dancer.booking.presentation.extensions.launch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class AccountVM(
    private val signOutRepo: SignOutRepo,
    private val deleteAccountRepo: DeleteAccountRepo,
    private val appNotifications: AppNotifications
) : AppViewModel() {
    val signOutSuccess = MutableStateFlow(false)


    fun logout() = launch(loading, error) {
        signOutRepo()
        appNotifications.cancelAll()
        signOutSuccess.value = true
    }

    fun delete() = launch(loading, error) {
        deleteAccountRepo()
    }

}

class SignOutRepo(
    private val userLocalSource: UserLocalSource,
    private val userApi: UserApi
) {

    suspend operator fun invoke() {
        userApi.logout().await()
        userLocalSource.logout()
    }
}

class FetchUserRepo(
    private val userLocalSource: UserLocalSource,
    private val userFactory: UserFactory,
    private val languageRepo: LanguageRepo
) {
    operator fun invoke(): IUser? {
        return userFactory.create(userLocalSource.getUserDto(), languageRepo.getLanguageDisplay())
    }

    fun currentDTO() = userLocalSource.getUserDto()

    fun live(): Flow<IUser?> {
        return userLocalSource.getUserLive().map {
            userFactory.create(userLocalSource.getUserDto(), languageRepo.getLanguageDisplay())
        }
    }
}

class DeleteAccountRepo(
    private val context: Context,
    private val userApi: UserApi,
    private val userLocalSource: UserLocalSource
) {
    suspend operator fun invoke() {
        userApi.delete(DeleteAccountForm().apply {
            macAddress = context.getDeviceID()
        }).await()
        userLocalSource.logout()
    }
}
