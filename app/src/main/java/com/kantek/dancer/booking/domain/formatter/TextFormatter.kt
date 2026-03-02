package com.kantek.dancer.booking.domain.formatter

import androidx.compose.ui.graphics.Color
import com.google.common.net.UrlEscapers
import com.kantek.dancer.booking.app.AppConfig
import com.kantek.dancer.booking.domain.model.response.LanguageDTO
import com.kantek.dancer.booking.presentation.theme.Colors
import java.net.URLDecoder

class TextFormatter {
    fun cleanPhoneNumber(phoneNumber: String): String {
        return phoneNumber.replace(" ", "")
            .replace("-", "")
            .replace("(", "")
            .replace(")", "")
            .replace(" ", "")
    }

    fun formatNotificationID(dataId: Int?): String {
        return "#$dataId"
    }

    fun getColorWithStatus(status: Int): Color {
        return when (status) {
            AppConfig.Booking.Status.NEW -> Colors.Orange251
            AppConfig.Booking.Status.PENDING -> Colors.Blue75
            AppConfig.Booking.Status.COMPLETE -> Colors.Blue148
            else -> Colors.Red247
        }
    }

    fun getLanguage(languages: List<LanguageDTO>?): String {
        return if (languages.isNullOrEmpty()) ""
        else languages[0].name
    }

    fun formatPhone(phoneNumber: String?): String? {
        val cleaned = phoneNumber?.replace("[^\\d]".toRegex(), "")
        return if (cleaned?.length == 10) {
            "(${cleaned.substring(0, 3)}) ${cleaned.substring(3, 6)}-${cleaned.substring(6, 10)}"
        } else phoneNumber
    }

    fun decodeEmoji(message: String?): String? {
        return try {
            URLDecoder.decode(message?.replace("+", "<plus>"), "UTF-8").replace("<plus>", "+")
        } catch (e: Exception) {
            message
        }
    }

    fun encodeEmoji(message: String): String {
        return try {
            UrlEscapers.urlFragmentEscaper().escape(message)
        } catch (e: Exception) {
            message
        }
    }

}