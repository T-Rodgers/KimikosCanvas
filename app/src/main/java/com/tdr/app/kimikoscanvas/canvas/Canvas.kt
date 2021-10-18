package com.tdr.app.kimikoscanvas.canvas

import com.google.firebase.storage.StorageReference

data class Canvas(val name: String, val imageRef: StorageReference) {
}