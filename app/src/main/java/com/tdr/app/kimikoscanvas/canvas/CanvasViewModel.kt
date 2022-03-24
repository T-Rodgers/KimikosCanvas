package com.tdr.app.kimikoscanvas.canvas

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tdr.app.kimikoscanvas.utils.Event
import com.tdr.app.kimikoscanvas.utils.FirebaseUtils
import kotlinx.coroutines.launch

const val PRODUCTS_PATH = "canvases"

enum class FirebaseApiStatus {
    DONE,
    LOADING,
    ERROR
}

class CanvasViewModel(app: Application) :
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

            try {
                FirebaseUtils().retrieveCanvases(object : FirebaseUtils.FirebaseServiceCallback {
                    override fun onProductListCallback(value: List<Canvas>) {
                        _canvases.value = value
                    }
                })

                _status.value = FirebaseApiStatus.DONE
            } catch (e: Exception) {
                e.printStackTrace()
                _status.value = FirebaseApiStatus.ERROR
                _statusMessage.value = Event("Firebase is in offline mode. Check internet")
            }
        }
    }

    fun clearItemList() {
        _canvases.value = ArrayList()
    }
}
