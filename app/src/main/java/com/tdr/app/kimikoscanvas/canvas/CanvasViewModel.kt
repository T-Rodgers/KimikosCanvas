package com.tdr.app.kimikoscanvas.canvas

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tdr.app.kimikoscanvas.data.CanvasDTO
import com.tdr.app.kimikoscanvas.data.CanvasDataSource
import com.tdr.app.kimikoscanvas.data.Result
import com.tdr.app.kimikoscanvas.utils.Event
import kotlinx.coroutines.launch

const val PRODUCTS_PATH = "canvases"

enum class FirebaseApiStatus {
    DONE,
    LOADING,
    ERROR
}

class CanvasViewModel(app: Application, private val dataSource: CanvasDataSource) :
    AndroidViewModel(app) {

    private val _statusMessage = MutableLiveData<Event<String>?>()
    val statusMessage : LiveData<Event<String>?>
    get() = _statusMessage

    private val _status = MutableLiveData<FirebaseApiStatus>()
    val status: LiveData<FirebaseApiStatus>
        get() = _status

    private val _canvases = MutableLiveData<List<Canvas>>()
    val canvases: LiveData<List<Canvas>>
        get() = _canvases

    private val _navigateToDetails = MutableLiveData<Boolean>()
    val navigateToDetails: LiveData<Boolean>
        get() = _navigateToDetails

    fun onNavigateToDetails() {
        _navigateToDetails.value = true
    }

    fun doneNavigatingToDetails() {
        _navigateToDetails.value = false
    }

    fun retrieveImagesFromDatabase() {
        viewModelScope.launch {
            _status.value = FirebaseApiStatus.LOADING
                val result = dataSource.getCanvases()
                when (result) {
                    is Result.Success -> {
                        val dataList = ArrayList<Canvas>()
                        dataList.addAll((result.data as List<CanvasDTO>).map { canvas ->
                            //map the reminder data from the DB to the be ready to be displayed on the UI
                            Canvas(
                                canvas.name,
                                canvas.imageUrl,
                                canvas.latitude,
                                canvas.longitude,
                                canvas.id
                            )
                        })
                        _canvases.value = dataList
                        _status.value = FirebaseApiStatus.DONE
                    }
                    is Result.Error -> {
                        _statusMessage.value = Event("Error Retrieving Canvases")
                        _status.value = FirebaseApiStatus.ERROR
                    }
                }
        }
    }

    fun clearItemList() {
        _canvases.value = ArrayList()
    }
}
