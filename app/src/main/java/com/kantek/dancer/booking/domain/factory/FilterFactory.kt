package com.kantek.dancer.booking.domain.factory

import android.content.Context
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.domain.model.response.filter.CityDTO
import com.kantek.dancer.booking.domain.model.response.filter.SpecialityDTO
import com.kantek.dancer.booking.domain.model.response.filter.StateDTO
import com.kantek.dancer.booking.domain.model.ui.search.ICity
import com.kantek.dancer.booking.domain.model.ui.search.ISpeciality
import com.kantek.dancer.booking.domain.model.ui.search.IState

class FilterFactory {

    fun createStates(context: Context, its: List<StateDTO>): List<IState> {
        val states = listOf(StateDTO(-1, context.getString(R.string.filter_all_state))) + its
        return states.map(::createState)
    }

    private fun createState(it: StateDTO): IState {
        return object : IState {
            override val id: Int
                get() = it.id
            override val name: String
                get() = it.name

            override fun toString(): String {
                return name
            }
        }
    }

    private fun createCity(it: CityDTO): ICity {
        return object : ICity {
            override val id: Int
                get() = it.id
            override val name: String
                get() = it.name

            override fun toString(): String {
                return name
            }
        }
    }

    private fun createSpeciality(it: SpecialityDTO): ISpeciality {
        return object : ISpeciality {
            override val id: Int
                get() = it.id
            override val name: String
                get() = it.name

            override fun toString(): String {
                return name
            }
        }
    }

    fun createCities(its: List<CityDTO>): List<ICity> {
        return its.map(::createCity)
    }

    fun createSpecialities(its: List<SpecialityDTO>): List<ISpeciality> {
        return its.map(::createSpeciality)
    }
}