package com.tdr.data.firebase

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Canvas(
    val name: String? = null,
    val imageUrl: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val id: String = UUID.randomUUID().toString()
) : Parcelable
