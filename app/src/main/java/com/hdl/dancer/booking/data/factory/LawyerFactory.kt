package com.hdl.dancer.booking.data.factory

import android.content.Context
import android.support.core.extensions.safe
import com.hdl.dancer.booking.R
import com.hdl.dancer.booking.app.AppConfig
import com.hdl.dancer.booking.data.formatter.TimeFormatter
import com.hdl.dancer.booking.data.model.response.LanguageDTO
import com.hdl.dancer.booking.data.model.response.lawyer.LawyerDTO
import com.hdl.dancer.booking.data.model.response.lawyer.ReviewDTO
import com.hdl.dancer.booking.domain.model.review.IReview
import com.hdl.dancer.booking.domain.model.user.ILawyer

class LawyerFactory(private val timeFormatter: TimeFormatter) {
    private fun createLawyer(it: LawyerDTO?): ILawyer {
        return object : ILawyer {
            override val owner: LawyerDTO?
                get() = it
            override val id: Int
                get() = it?.id.safe()
            override val fullName: String
                get() = it?.name.safe()
            override val avatarURL: String
                get() = it?.avatar_url.safe()
            override val exp: String
                get() = it?.exp.toString()
            override val cases: String
                get() = it?.cases.toString()
            override val rating: Float
                get() = if (it?.rating_avg.safe() == 0f) 5f else it?.rating_avg.safe()
            override val reviewCount: Int
                get() = it?.total_reviews.safe()
        }
    }

    fun createList(its: List<LawyerDTO>?): List<ILawyer>? {
        return its?.map(::createLawyer)
    }

    fun formatLanguageDisplay(
        context: Context,
        its: List<LanguageDTO>?
    ): String? {
        return if (its.isNullOrEmpty()) null
        else its.map { it.lang_key }.joinToString(separator = ", ") {
            when (it) {
                AppConfig.Languages.EN -> context.getString(R.string.language_en)
                AppConfig.Languages.ES -> context.getString(R.string.language_es)
                AppConfig.Languages.VI -> context.getString(R.string.language_vi)
                else -> ""
            }
        }
    }

    fun createReviews(its: List<ReviewDTO>?): List<IReview> {
        return its?.map(::createReview) ?: listOf()
    }

    private fun createReview(it: ReviewDTO): IReview {
        return object : IReview {
            override val id: Int
                get() = it.id.safe()
            override val name: String
                get() = it.user_name.safe()
            override val avatarURL: String
                get() = it.user.avatar.safe()
            override val createAt: String
                get() = timeFormatter.timeAgo(it.created_at)
            override val rating: Float
                get() = it.rating.safe()
            override val contents: String
                get() = it.review_text.safe()
        }
    }
}