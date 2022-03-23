package com.tdr.app.kimikoscanvas.data


interface CanvasDataSource {
        suspend fun getCanvases(): Result<List<CanvasDTO>>
        suspend fun saveCanvas(canvas: CanvasDTO)
        suspend fun getCanvas(id: String): Result<CanvasDTO>
    }
