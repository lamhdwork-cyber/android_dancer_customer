package com.kantek.dancer.booking.data.local

import android.content.Context
import android.support.persistent.cache.GsonCaching
import com.kantek.dancer.booking.domain.model.entity.FilterEntity
import com.kantek.dancer.booking.domain.model.form.LawyerFilterForm

class FilterLocalSource(context: Context) {

    private var caching: GsonCaching = GsonCaching(context)

    private var filter: FilterEntity? by caching.reference(FilterEntity::class.java.name)


    fun getStateID() = filter?.stateID ?: -1

    fun getCityID() = filter?.cityID ?: -1

    fun getLanguageID() = filter?.languageID ?: -1

    fun getSpecialities() = filter?.specialities

    fun saveStateID(id: Int) {
        filter = if (filter == null)
            FilterEntity(stateID = id)
        else filter?.copy(stateID = id)
    }

    fun get(): FilterEntity? = filter

    fun saveFilter(it: LawyerFilterForm) {
        filter = if (filter == null)
            FilterEntity(
                cityID = it.citySelected?.id,
                languageID = it.languageSelected?.id,
                specialities = it.specialitySelected?.map { it.id }
            )
        else filter?.copy(
            cityID = it.citySelected?.id,
            languageID = it.languageSelected?.id,
            specialities = it.specialitySelected?.map { it.id }
        )
    }

    fun reset() {
        filter = filter?.copy(
            stateID = -1,
            cityID = null,
            languageID = null,
            specialities = null
        )
    }
}