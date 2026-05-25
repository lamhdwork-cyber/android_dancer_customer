package com.kantek.dancer.booking.data.model.form

import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.data.extension.resourceError

data class ReviewForm(
    var contact_request_id: String? = "",
    var rating: Float = 5f,
    var review_text: String = "",
) {
    fun valid() {
        if (review_text.isBlank()) resourceError(R.string.error_blank_content)
    }
}