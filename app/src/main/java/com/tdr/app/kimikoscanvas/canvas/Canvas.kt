package com.tdr.app.kimikoscanvas.canvas

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Canvas(val name: String? =null, val imageUrl: String? = null, val price: Int? = null) :
    Parcelable {
}