package com.kantek.dancer.booking.app

import android.annotation.SuppressLint
import android.os.Build
import java.util.Locale

object AppConfig {
    const val END_POINT: String = "https://dancer.kendemo.com/api/v1/"
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
        /** API / DTO string values for [com.kantek.dancer.booking.data.model.response.BookingDTO.status]. */
        object Status {
            const val PENDING = "pending"
            const val SCHEDULED = "scheduled"
            const val WAITING = "waiting"
            const val ACCEPTED = "accepted"
            const val READY = "ready"
            const val CANCELLED = "cancelled"
        }
    }

    /**
     * Values from [com.kantek.dancer.booking.data.model.response.UserDTO.role] after login.
     */
    object UserRole {
        const val USER = "user"
        const val CLUB_MANAGER = "dancer"

        fun isClubManager(role: String?): Boolean =
            role?.trim()?.equals(CLUB_MANAGER, ignoreCase = true) == true

        /** Only [USER] or [CLUB_MANAGER] may use this customer app after login. */
        fun isAppLoginAllowed(role: String?): Boolean {
            val r = role?.trim().orEmpty()
            if (r.isEmpty()) return false
            return r.equals(USER, ignoreCase = true) ||
                r.equals(CLUB_MANAGER, ignoreCase = true)
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

