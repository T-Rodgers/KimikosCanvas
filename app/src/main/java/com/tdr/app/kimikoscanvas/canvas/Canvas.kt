package com.tdr.app.kimikoscanvas.canvas

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.storage.StorageReference

@IgnoreExtraProperties
data class Canvas(val name: String? =null, val imageUrl: String? = null, val price: Int? = null) {
}