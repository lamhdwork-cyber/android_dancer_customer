package com.kantek.dancer.booking.app

import android.annotation.SuppressLint
import android.os.Build
import java.util.Locale

object AppConfig {
    const val END_POINT: String = "https://law-booking.kendemo.com/api/"
    const val SOCKET_IO: String = "https://law-booking.kendemo.com:6016"

    const val PER_PAGE = 15
    const val OTP_TIME_OUT = 60

    @SuppressLint("ConstantLocale")
    val deviceInfo = "${Build.MANUFACTURER.uppercase(Locale.getDefault())} ${
        Build.MODEL.uppercase(
            Locale.getDefault()
        )
    } -  Android ${Build.VERSION.RELEASE}"

    object OTPType {
        const val REGISTER = "1"
        const val FORGOT_PASSWORD = "2"
    }

    object Languages {
        const val EN = "en"
        const val VI = "vi"
        const val ES = "es"
    }

    object Booking {
        object Status {
            const val NEW = 1//FB923C
            const val PENDING = 2//4B93E5
            const val COMPLETE = 3//94A3B8
            const val CANCELED = 5//F75D5D
        }
    }

    object NotificationType {
        object Push {
            const val NONE = 0//Default
            const val CONTACT_REQUEST_COMPLETED = 1
            const val CHAT = 3
        }
    }
}

