package com.tdr.app.kimikoscanvas.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
interface CanvasDataSource {

    fun getCanvasesFromFirebase() : Flow<Result<List<Canvas>>>
    fun getConnectionStatus() : Flow<Result<Boolean>>
}