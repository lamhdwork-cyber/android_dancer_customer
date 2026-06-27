package com.hdl.dancer.booking.presentation.screen.introduce

import com.hdl.dancer.booking.R
import com.hdl.dancer.booking.data.factory.IntroduceFactory
import com.hdl.dancer.booking.data.model.response.introduce.IntroduceDTO
import com.hdl.dancer.booking.domain.model.introduce.IIntroduce

class FetchIntroduceRepo(
    private val introduceFactory: IntroduceFactory
) {
    suspend operator fun invoke(): List<IIntroduce> {
        val rs = listOf(
            IntroduceDTO(
                backgroundRes = R.drawable.bg_introduce_2,
                iconRes = R.drawable.ic_intro_2,
                titleRes = R.string.introduce_title_3,
                descriptionRes = R.string.introduce_description_3
            )
        )
        return introduceFactory.createList(rs)
    }
}
