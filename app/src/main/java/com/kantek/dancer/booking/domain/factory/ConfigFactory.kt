package com.kantek.dancer.booking.domain.factory

import android.support.core.extensions.safe
import com.kantek.dancer.booking.domain.formatter.TextFormatter
import com.kantek.dancer.booking.domain.model.response.config.BannerDTO
import com.kantek.dancer.booking.domain.model.response.config.FAQsDTO
import com.kantek.dancer.booking.domain.model.response.config.SettingDTO
import com.kantek.dancer.booking.domain.model.ui.config.IBanner
import com.kantek.dancer.booking.domain.model.ui.config.IFAQs
import com.kantek.dancer.booking.domain.model.ui.config.ISetting

class ConfigFactory(private val textFormatter: TextFormatter) {

    fun createBanners(its: List<BannerDTO>): List<IBanner> {
        return its.map(::createBanner)
    }

    private fun createBanner(it: BannerDTO): IBanner {
        return object : IBanner {
            override val id: Long
                get() = it.id.safe()
            override val title: String
                get() = it.title.safe()
            override val dataURL: String
                get() = it.source_url.safe()
            override val hasVideo: Boolean
                get() = it.mime_type == "video/mp4"
            override val linkURL: String
                get() = it.link.safe()
        }
    }

    fun createFAQsList(its: List<FAQsDTO>?): List<IFAQs> {
        return its?.map(::createFAQs) ?: listOf()
    }

    private fun createFAQs(it: FAQsDTO): IFAQs {
        return object : IFAQs {
            override val id: Int
                get() = it.id.safe()
            override val question: String
                get() = it.question.safe()
            override val answer: String
                get() = it.answer.safe()
        }
    }

    fun createSetting(it: SettingDTO): ISetting {
        return object : ISetting {
            override val address: String
                get() = it.setting.address?.value.safe()
            override val phone: String
                get() = it.setting.hotline?.value.safe()
            override val phoneDisplay: String
                get() = textFormatter.formatPhone(phone).safe()
            override val email: String
                get() = it.setting.supportEmail?.value.safe()
        }
    }
}