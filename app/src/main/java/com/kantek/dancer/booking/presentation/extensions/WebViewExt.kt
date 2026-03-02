package com.kantek.dancer.booking.presentation.extensions

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner


@SuppressLint("SetJavaScriptEnabled")
fun WebView.loadUrlData(
    owner: LifecycleOwner? = null,
    url: String,
    onLoading: (Boolean) -> Unit,
    onError: (() -> Unit)? = null
) {
    WebView.setWebContentsDebuggingEnabled(true)
    owner?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) {
            owner.lifecycle.removeObserver(this)
            this@loadUrlData.destroy()
            super.onDestroy(owner)
        }
    })
    onLoading.invoke(true)
    this.settings.apply {
        javaScriptEnabled = true
        domStorageEnabled  = true
        loadsImagesAutomatically = true
        mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        setSupportZoom(true)
        userAgentString =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119 Safari/537.36"
    }
    this.apply {
        webChromeClient = WebChromeClient()
        CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
        CookieManager.getInstance().setAcceptCookie(true)
    }
    this.webViewClient = object : WebViewClient() {

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            onLoading.invoke(false)
            Log.d("onPageFinished", "${view?.progress} $url")
        }

        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            super.onReceivedError(view, request, error)
            onLoading.invoke(false)
            onError?.invoke()
            Log.e("onReceivedError", "${view?.progress} $url")
        }

        @Deprecated("Deprecated in Java")
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            if (url.startsWith("mailto:") || url.startsWith("tel:")) {
                view.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            } else view.loadUrl(url)
            return true
        }
    }
    this.loadUrl(url)
}