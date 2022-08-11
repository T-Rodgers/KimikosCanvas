package com.tdr.app.kimikoscanvas.canvas

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tdr.app.kimikoscanvas.data.Canvas
import com.tdr.app.kimikoscanvas.data.CanvasDataSource
import com.tdr.app.kimikoscanvas.utils.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

enum class FirebaseApiStatus {
    DONE,
    LOADING,
    ERROR
}

@ExperimentalCoroutinesApi
class CanvasViewModel(app: Application, private val dataSource: CanvasDataSource) :
    AndroidViewModel(app) {

    private val _statusMessage = MutableLiveData<Event<String>?>()
    val statusMessage: LiveData<Event<String>?>
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

    init {
        retrieveImagesFromDatabase()
    }

    @ExperimentalCoroutinesApi
    fun retrieveImagesFromDatabase() {
        _status.value = FirebaseApiStatus.LOADING
        viewModelScope.launch {
            dataSource.getCanvasesFromFirebase().collect {
                when {
                    it.isSuccess -> {
                        val list = it.getOrNull()
                        _canvases.value = list?.let { canvas -> sortByName(canvas) }
                        _status.value = FirebaseApiStatus.DONE

                    }
                    it.isFailure -> {
                        it.exceptionOrNull()?.printStackTrace()
                        _statusMessage.value = Event("Error")
                        _status.value = FirebaseApiStatus.ERROR
                    }
                }
            }
        }
    }

    private fun sortByName(list: List<Canvas>) = list.sortedBy { it.name }

    fun clearItemList() {
        _canvases.value = ArrayList()
    }


}
