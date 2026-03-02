package com.kantek.dancer.booking.presentation.helper.login

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.presentation.widget.SocialLoginButton
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun GoogleSignInButton(onResult: (GoogleSignInAccount) -> Unit) {
    val context = LocalContext.current
    val signInClient = remember { context.getGoogleSignInClient() }
    val coroutineScope = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            onResult(account)
        } catch (e: ApiException) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    SocialLoginButton(R.drawable.ic_google) {
        coroutineScope.launch {
            signInClient.signOut().await()
            launcher.launch(signInClient.signInIntent)
        }
    }
}