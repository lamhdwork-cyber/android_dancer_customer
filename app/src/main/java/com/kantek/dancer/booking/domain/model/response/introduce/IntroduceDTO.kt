package com.kantek.dancer.booking.domain.model.response.introduce

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class IntroduceDTO(
    @DrawableRes val backgroundRes: Int = 0,
    @DrawableRes val iconRes: Int,
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int
)
