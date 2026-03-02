package com.kantek.dancer.booking.domain.model.form

import com.kantek.dancer.booking.domain.model.ui.search.ICity
import com.kantek.dancer.booking.domain.model.ui.search.ISpeciality
import com.kantek.dancer.booking.domain.model.ui.search.IState
import com.kantek.dancer.booking.domain.model.ui.user.ILanguage

data class LawyerFilterForm(
    var stateSelected: IState? = null,
    var citySelected: ICity? = null,
    var languageSelected: ILanguage? = null,
    var specialitySelected: List<ISpeciality>? = null
)