package com.tdr.data.firebase

import kotlinx.coroutines.flow.Flow


interface CanvasRepository {

    fun getCanvasesFromFirebase() : Flow<Result<List<Canvas>>>
}