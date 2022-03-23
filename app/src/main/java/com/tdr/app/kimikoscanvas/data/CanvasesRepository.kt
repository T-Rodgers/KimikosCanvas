package com.tdr.app.kimikoscanvas.data

import com.tdr.app.kimikoscanvas.utils.FirebaseUtils
import kotlinx.coroutines.*
import timber.log.Timber

class CanvasesRepository(
    private val canvasesDao: CanvasesDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CanvasDataSource {

    var list = listOf<CanvasDTO>()

    override suspend fun getCanvases(): Result<List<CanvasDTO>> = withContext(ioDispatcher) {
        return@withContext try {
            retrieveFirebaseItems()
            Result.Success(canvasesDao.getCanvases())
        } catch (e: Exception) {
            Timber.i("getCanvases Error")
            Result.Error(e)
        }
    }

    private suspend fun retrieveFirebaseItems() = withContext(ioDispatcher) {

        try {
            FirebaseUtils().retrieveCanvases(object : FirebaseUtils.FirebaseServiceCallback {
                override fun onProductListCallback(value: List<CanvasDTO>) {
                    runBlocking {
                        saveCanvas(value[0])
                    }
                }
            })
            Timber.i(list[0].name)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override suspend fun saveCanvas(canvas: CanvasDTO) = withContext(ioDispatcher) {
        canvasesDao.saveCanvas(canvas)
    }
}