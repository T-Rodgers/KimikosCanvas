package com.tdr.app.kimikoscanvas.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [CanvasDTO::class], version = 1)
abstract class CanvasesDatabase: RoomDatabase() {

        abstract fun canvasesDao(): CanvasesDao

}