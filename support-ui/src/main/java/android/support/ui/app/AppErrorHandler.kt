package android.support.ui.app

import android.app.AlertDialog
import android.app.Service
import android.support.core.network.exception.ExpiredTokenException
import android.support.core.network.exception.InternalServerException
import android.support.ui.app.BaseComponentAct
import android.support.ui.R
import android.support.ui.exception.ResourceException
import com.google.gson.JsonSyntaxException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

interface AppErrorHandler {
    fun handle(activity: BaseComponentAct, error: Throwable)
    fun handle(service: Service, error: Throwable)
}

open class AppErrorHandlerImpl : AppErrorHandler {

    override fun handle(service: Service, error: Throwable) {
        AlertDialog.Builder(service.applicationContext).setMessage(error.message)
            .create()
            .show()
    }

    override fun handle(activity: BaseComponentAct, error: Throwable) {
        when (error) {
            is ResourceException -> activity.showNotification(error.resource)
            is UnknownHostException -> activity.showNotification(R.string.core_error_no_internet)
            is ConnectException,
            is SocketException,
            is SocketTimeoutException,
            is JsonSyntaxException -> activity.showNotification(R.string.core_error_server)

            is ExpiredTokenException -> activity.showExpiredTokenDialog(true)
            is InternalServerException -> activity.showNotification(R.string.core_error_internal_server)

            else -> {
                if (error.message == "Unauthorized !")
                    activity.showExpiredTokenDialog(true)
                else activity.showNotification(error.message ?: "error")
            }
        }
    }
}
