package com.kantek.dancer.booking.data.remote.socket

import android.annotation.SuppressLint
import com.kantek.dancer.booking.app.AppConfig
import com.kantek.dancer.booking.presentation.extensions.loge
import io.socket.client.IO
import io.socket.client.Socket
import okhttp3.OkHttpClient
import java.net.URISyntaxException
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class SocketClient {
    var mSocket: Socket? = null
    private var mServerSocket = AppConfig.SOCKET_IO
    private var onConnectSuccessListener: (() -> Unit)? = null

    init {
        loge("Create!!!")
        setupSocket()
        connectSocket()
    }

    private fun setupSocket() {
        try {
            mSocket = IO.socket(mServerSocket)
//            val options = IO.Options()
//            options.path = "/v1"
//            mSocket = IO.socket(mServerSocket, options)
        } catch (e: URISyntaxException) {
            throw RuntimeException(e)
        }
        setupSSL()
    }


    private fun connectSocket() {
        mSocket?.on(Socket.EVENT_CONNECT) {
            onConnectSuccessListener?.invoke()
            loge("success")
        }?.on(Socket.EVENT_CONNECT_ERROR) {
            loge("error")
        }?.on(Socket.EVENT_DISCONNECT) {
            loge("disconnect")
        }
        mSocket?.connect()
    }

    fun connectIfNeed(onConnectSuccess: () -> Unit) {
        onConnectSuccessListener = onConnectSuccess
        if (mSocket == null) {
            setupSocket()
            connectSocket()
        }
        if (!mSocket?.connected()!!)
            connectSocket()
        else onConnectSuccess.invoke()
    }

    @SuppressLint("TrustAllX509TrustManager")
    private fun setupSSL(): Socket? {
        try {
            val myHostnameVerifier = HostnameVerifier { _, _ -> true }
            val mySSLContext = SSLContext.getInstance("TLS")
            val trustAllCerts = arrayOf<TrustManager>(@SuppressLint("CustomX509TrustManager")
            object : X509TrustManager {
                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }


                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {
                }
            })

            mySSLContext.init(null, trustAllCerts, java.security.SecureRandom())

            val okHttpClient = OkHttpClient.Builder()
                .hostnameVerifier(myHostnameVerifier)

                .sslSocketFactory(mySSLContext.socketFactory,
                    @SuppressLint("CustomX509TrustManager")
                    object : X509TrustManager {
                        @Throws(CertificateException::class)
                        override fun checkClientTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {

                        }

                        @Throws(CertificateException::class)
                        override fun checkServerTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {

                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }
                    })
                .build()
            val options = IO.Options()
            options.callFactory = okHttpClient
            options.webSocketFactory = okHttpClient

            mSocket = IO.socket(mServerSocket, options)
        } catch (e: URISyntaxException) {
            throw RuntimeException(e)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        }
        return mSocket
    }

    fun disconnect() {
        mSocket?.disconnect()
        mSocket?.off(Socket.EVENT_CONNECT)
        mSocket?.off(Socket.EVENT_CONNECT_ERROR)
    }

}