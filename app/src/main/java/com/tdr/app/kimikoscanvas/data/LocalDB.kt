package com.tdr.app.kimikoscanvas.data

import android.content.Context
import androidx.room.Room

/**
 * Singleton class that is used to create a reminder db
 */
object LocalDB {

    /**
     * static method that creates a reminder class and returns the DAO of the reminder
     */
    fun createCanvasesDao(context: Context): CanvasesDao {
        return Room.databaseBuilder(
            context.applicationContext,
            CanvasesDatabase::class.java, "canvases.db"
        ).build().canvasesDao()
    }

}