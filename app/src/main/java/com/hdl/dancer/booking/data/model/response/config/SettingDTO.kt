package com.hdl.dancer.booking.data.model.response.config

import com.google.gson.annotations.SerializedName

data class SettingDTO(
    val setting: SettingDataDTO
)

data class SettingDataDTO(
    @SerializedName("support_email")
    val supportEmail: ValueDTO?,
    val hotline: ValueDTO?,
    val address: ValueDTO?
)

data class ValueDTO(
    @SerializedName("val")
    val value: String
)