package com.tdr.app.kimikoscanvas.canvas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tdr.app.kimikoscanvas.utils.FirebaseUtils
import kotlinx.coroutines.launch
import timber.log.Timber

const val PRODUCTS_PATH = "canvases"
enum class FirebaseApiStatus{
    DONE,
    LOADING,
    ERROR
}

class CanvasViewModel() : ViewModel() {

    private val _status = MutableLiveData<FirebaseApiStatus>()
    val status : LiveData<FirebaseApiStatus>
    get() = _status

    private val _canvases = MutableLiveData<List<Canvas>>()
    val canvases: LiveData<List<Canvas>>
        get() = _canvases

    private val _navigateToDetails = MutableLiveData<Boolean>()
    val navigateToDetails: LiveData<Boolean>
        get() = _navigateToDetails

    init {
        retrieveImagesFromDatabase()
        Timber.i("CanvasViewModel Initialized")

    }

    fun onNavigateToDetails() {
        _navigateToDetails.value = true
    }

    fun doneNavigatingToDetails() {
        _navigateToDetails.value = false
    }

    private fun retrieveImagesFromDatabase() {
        viewModelScope.launch {
            _status.value = FirebaseApiStatus.LOADING
            try {
                FirebaseUtils().retrieveProducts(object : FirebaseUtils.FirebaseServiceCallback {
                    override fun onProductListCallback(retrievedList: List<Canvas>) {
                        _canvases.value = retrievedList
                        Timber.i("${retrievedList.size}")
                        _status.value = FirebaseApiStatus.DONE
                    }
                })

            } catch (e: Exception) {
                _status.value = FirebaseApiStatus.ERROR
                _canvases.value = ArrayList()
            }

        }
    }
}
