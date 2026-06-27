package com.hdl.dancer.booking.data.remote.api

import com.hdl.dancer.booking.data.helper.network.ApiAsync
import com.hdl.dancer.booking.data.model.response.filter.CityDTO
import com.hdl.dancer.booking.data.model.response.filter.SpecialityDTO
import com.hdl.dancer.booking.data.model.response.filter.StateDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface FilterApi {

    @GET("general/state?per_page=-1")
    fun fetchAllState(): ApiAsync<List<StateDTO>>

    @GET("general/city?per_page=-1")
    fun fetchAllCity(@Query("state_id") stateID: Int): ApiAsync<List<CityDTO>>

    @GET("type_service/service?type_id=-1&per_page=-1")
    fun fetchAllSpeciality(): ApiAsync<List<SpecialityDTO>>
}