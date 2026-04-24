package com.kantek.dancer.booking.domain.model.ui.search

interface IDancerDetail : IDancer {
    val age: Int
    val dancerCode: String
    val danceStyles: List<String>
    val gallery: List<String>
    val experience: Int
    val hourlyRate: String
    val totalReviews: Int
}
