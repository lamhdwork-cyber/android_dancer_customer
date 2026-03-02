package com.kantek.dancer.booking.domain.factory

import android.support.core.extensions.safe
import com.kantek.dancer.booking.domain.formatter.TimeFormatter
import com.kantek.dancer.booking.domain.model.response.legal.LegalAnswerDTO
import com.kantek.dancer.booking.domain.model.response.legal.LegalCategoryDTO
import com.kantek.dancer.booking.domain.model.response.legal.LegalQuestionDTO
import com.kantek.dancer.booking.domain.model.ui.faqs.ILegalAnswer
import com.kantek.dancer.booking.domain.model.ui.faqs.ILegalCategory
import com.kantek.dancer.booking.domain.model.ui.faqs.ILegalQuestion

class FAQsThreadsFactory(private val timeFormatter: TimeFormatter) {

    fun createCategories(its: List<LegalCategoryDTO>): List<ILegalCategory> {
        return its.map(::createCategory)
    }

    fun createCategory(it: LegalCategoryDTO): ILegalCategory {
        return object : ILegalCategory {
            override val id: Int
                get() = it.id.safe()
            override val name: String
                get() = it.name.safe()
        }
    }

    fun createQuestion(it: LegalQuestionDTO): ILegalQuestion {
        return object : ILegalQuestion {
            override val id: Int
                get() = it.id.safe()
            override val name: String
                get() = it.description.safe()
        }
    }

    fun createQuestions(its: List<LegalQuestionDTO>?): List<ILegalQuestion> {
        return its?.map(::createQuestion)?: listOf()
    }

    fun createAnswers(items: List<LegalAnswerDTO>?): List<ILegalAnswer> {
        return items?.map(::createAnswer)?: listOf()
    }

    private fun createAnswer(it: LegalAnswerDTO): ILegalAnswer {
        return object : ILegalAnswer {
            override val id: Int
                get() = it.id.safe()
            override val name: String
                get() = it.user.name.safe()
            override val avatarURL: String
                get() = it.user.avatar_url.safe()
            override val timeAgo: String
                get() = timeFormatter.timeAgo(it.time)
            override val content: String
                get() = it.description.safe()
        }
    }

}
