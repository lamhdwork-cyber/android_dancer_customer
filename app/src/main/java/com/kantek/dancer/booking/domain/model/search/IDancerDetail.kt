package com.kantek.dancer.booking.domain.model.search

interface IDancerDetail : IDancer {
    /** Club the dancer belongs to; used for booking add-performer list. */
    val clubId: String
    val clubName: String
    val clubCoverImage: String
    val age: Int
    val dancerCode: String
    val danceStyles: List<String>
    val gallery: List<String>
    val experience: Int
    val hourlyRate: String
    val totalReviews: Int
}
