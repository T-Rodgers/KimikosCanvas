package com.tdr.app.kimikoscanvas.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CanvasesDao {

    /**
     * @return all canvases.
     */
    @Query("SELECT * FROM canvases")
    suspend fun getCanvases(): List<CanvasDTO>

    /**
     * Insert a canvas in the database. If the canvas already exists, replace it.
     *
     * @param canvas the canvas to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCanvas(canvas: CanvasDTO)
}