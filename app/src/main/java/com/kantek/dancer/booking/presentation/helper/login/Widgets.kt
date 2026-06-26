package com.kantek.dancer.booking.presentation.helper.login

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.kantek.dancer.booking.R
import android.support.ui.widget.SocialLoginButton
import kotlinx.coroutines.launch

@Composable
fun GoogleSignInButton(onResult: (GoogleIdTokenCredential) -> Unit) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    SocialLoginButton(R.drawable.ic_google) {
        coroutineScope.launch {
            try {
                onResult(context.getGoogleIdCredential())
            } catch (e: GetCredentialException) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
