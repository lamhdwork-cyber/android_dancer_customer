package com.kantek.dancer.booking.domain.formatter

import androidx.compose.ui.graphics.Color
import com.google.common.net.UrlEscapers
import com.kantek.dancer.booking.app.AppConfig
import com.kantek.dancer.booking.domain.extension.Format.FORMAT_DATE_TIME
import com.kantek.dancer.booking.domain.extension.formatWith
import com.kantek.dancer.booking.domain.extension.utcToDateLocal
import com.kantek.dancer.booking.domain.model.response.LanguageDTO
import com.kantek.dancer.booking.presentation.theme.Colors
import java.net.URLDecoder
import java.text.NumberFormat
import java.util.Locale

class TextFormatter {
    fun cleanPhoneNumber(phoneNumber: String): String {
        return phoneNumber.replace(" ", "")
            .replace("-", "")
            .replace("(", "")
            .replace(")", "")
            .replace(" ", "")
    }

    fun formatNotificationID(dataId: String?): String {
        return if (dataId.isNullOrBlank()) "" else "#$dataId"
    }

    fun formatNotificationDateTime(createdAt: String?): String {
        return createdAt?.utcToDateLocal().formatWith(FORMAT_DATE_TIME)
    }

    fun getColorWithStatus(status: String): Color {
        return when {
            status.equals(AppConfig.Booking.Status.PENDING, true) ||
                status.equals(AppConfig.Booking.Status.SCHEDULED, true) -> Colors.Orange251
            status.equals(AppConfig.Booking.Status.CONFIRMED, true) ||
                status.equals(AppConfig.Booking.Status.ACCEPTED, true) -> Colors.Blue75
            status.equals(AppConfig.Booking.Status.COMPLETED, true) -> Colors.Blue148
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

    fun formatCurrency(raw: String): String {
        val value = raw.toDoubleOrNull() ?: return raw
        val format = NumberFormat.getNumberInstance(Locale.US).apply {
            minimumFractionDigits = 0
            maximumFractionDigits = 2
        }
        return format.format(value)
    }

    fun formatBookingPrice(raw: String): String {
        return "$${formatCurrency(raw)}"
    }

}