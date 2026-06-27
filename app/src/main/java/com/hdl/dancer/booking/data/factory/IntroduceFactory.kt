package com.hdl.dancer.booking.data.factory

import com.hdl.dancer.booking.data.model.response.introduce.IntroduceDTO
import com.hdl.dancer.booking.domain.model.introduce.IIntroduce

class IntroduceFactory {
    fun createList(its: List<IntroduceDTO>): List<IIntroduce> {
        return its.map(::create)
    }

    fun create(it: IntroduceDTO): IIntroduce {
        return object : IIntroduce {
            override val backgroundRes: Int
                get() = it.backgroundRes
            override val iconRes: Int
                get() = it.iconRes
            override val titleRes: Int
                get() = it.titleRes
            override val descriptionRes: Int
                get() = it.descriptionRes
        }
    }
}
