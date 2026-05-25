package com.kantek.dancer.booking.domain.model.booking

import androidx.compose.ui.graphics.vector.ImageVector

interface IRoom {
    val id: String
    val name: String
    val services: String
    val price: String
    val priceDisplay: String
    val imageURL: String
    val imagePlaceholder: ImageVector
}

