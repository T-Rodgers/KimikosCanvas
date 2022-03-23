package com.tdr.app.kimikoscanvas.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "canvases")
data class CanvasDTO(
    @ColumnInfo(name = "name")val name: String? = null,
    @ColumnInfo(name = "img_url")val imageUrl: String? = null,
    @ColumnInfo(name = "latitude")val latitude: Double? = null,
    @ColumnInfo(name = "longitude")val longitude: Double? = null,
    @PrimaryKey @ColumnInfo(name = "entry_id")val id: String = UUID.randomUUID().toString()
) {
}