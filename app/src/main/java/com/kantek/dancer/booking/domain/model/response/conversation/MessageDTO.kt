package com.kantek.dancer.booking.domain.model.response.conversation

import com.google.gson.annotations.SerializedName

data class MessageDTO(
    val created_at: String? = "",
    @SerializedName("medias")
    val images: List<ImageDTO>? = null,
    val id: Long? = -1,
    val message: String? = "",
    val local_id: String? = "",
    val sender: User? = null
) {
    data class User(
        val avatar_url: String? = "",
        val id: Long? = 0L,
        val name: String? = "",
        val is_admin: Int = 1//1-Admin else 0
    )
}