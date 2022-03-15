package com.tdr.app.kimikoscanvas.data

import java.util.*

data class CanvasDTO(
    val name: String? = null,
    val imageUrl: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val id: String = UUID.randomUUID().toString()
) {
}