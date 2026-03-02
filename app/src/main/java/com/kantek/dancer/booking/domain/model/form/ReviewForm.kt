package com.kantek.dancer.booking.domain.model.form

import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.domain.extension.resourceError

data class ReviewForm(
    var contact_request_id: Int? = 0,
    var rating: Float = 5f,
    var review_text: String = "",
) {
    fun valid() {
        if (review_text.isBlank()) resourceError(R.string.error_blank_content)
    }
}