package com.kantek.dancer.booking.presentation.screen.introduce

import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.domain.factory.IntroduceFactory
import com.kantek.dancer.booking.domain.model.response.introduce.IntroduceDTO
import com.kantek.dancer.booking.domain.model.ui.introduce.IIntroduce

class FetchIntroduceRepo(
    private val introduceFactory: IntroduceFactory
) {
    suspend operator fun invoke(): List<IIntroduce> {
        val rs = listOf(
            IntroduceDTO(
                backgroundRes = 0,
                iconRes = R.drawable.ic_nav_search,
                titleRes = R.string.introduce_title_2,
                descriptionRes = R.string.introduce_description_2
            ),
            IntroduceDTO(
                backgroundRes = 0,
                iconRes = R.drawable.ic_avatar_consultant,
                titleRes = R.string.introduce_title_3,
                descriptionRes = R.string.introduce_description_3
            ),
            IntroduceDTO(
                backgroundRes = 0,
                iconRes = R.drawable.ic_nav_cases,
                titleRes = R.string.introduce_title_1,
                descriptionRes = R.string.introduce_description_1
            )
        )
        return introduceFactory.createList(rs)
    }
}
