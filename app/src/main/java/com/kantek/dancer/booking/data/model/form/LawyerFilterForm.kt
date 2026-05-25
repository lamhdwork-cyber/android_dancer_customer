package com.kantek.dancer.booking.data.model.form

import com.kantek.dancer.booking.domain.model.search.ICity
import com.kantek.dancer.booking.domain.model.search.ISpeciality
import com.kantek.dancer.booking.domain.model.search.IState
import com.kantek.dancer.booking.domain.model.user.ILanguage

data class LawyerFilterForm(
    var stateSelected: IState? = null,
    var citySelected: ICity? = null,
    var languageSelected: ILanguage? = null,
    var specialitySelected: List<ISpeciality>? = null
)