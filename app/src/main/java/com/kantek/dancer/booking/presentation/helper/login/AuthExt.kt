package com.kantek.dancer.booking.presentation.helper.login

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.kantek.dancer.booking.BuildConfig

/**
 * Launches the "Sign in with Google" flow via Credential Manager and returns the
 * resulting [GoogleIdTokenCredential] (id token + basic profile).
 *
 * Requires a Web OAuth client id in `BuildConfig.GOOGLE_WEB_CLIENT_ID`
 * (set via the `GOOGLE_WEB_CLIENT_ID` gradle property).
 */
suspend fun Context.getGoogleIdCredential(): GoogleIdTokenCredential {
    val googleIdOption = GetGoogleIdOption.Builder()
        .setServerClientId(BuildConfig.GOOGLE_WEB_CLIENT_ID)
        .setFilterByAuthorizedAccounts(false)
        .build()

    val request = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    val response = CredentialManager.create(this).getCredential(this, request)
    return GoogleIdTokenCredential.createFrom(response.credential.data)
}