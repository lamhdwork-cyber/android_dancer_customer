package com.hdl.dancer.booking.data.model.form

import com.hdl.dancer.booking.domain.model.search.ICity
import com.hdl.dancer.booking.domain.model.search.ISpeciality
import com.hdl.dancer.booking.domain.model.search.IState
import com.hdl.dancer.booking.domain.model.user.ILanguage

data class LawyerFilterForm(
    var stateSelected: IState? = null,
    var citySelected: ICity? = null,
    var languageSelected: ILanguage? = null,
    var specialitySelected: List<ISpeciality>? = null
)