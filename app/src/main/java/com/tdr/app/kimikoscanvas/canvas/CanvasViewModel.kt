package com.tdr.app.kimikoscanvas.canvas

import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.StorageReference
import com.tdr.app.kimikoscanvas.utils.FirebaseUtils
import kotlinx.coroutines.launch
import timber.log.Timber

//const val IMAGES_PATH = "images/landscapes/"
const val PRODUCTS_PATH = "canvases"

class CanvasViewModel() : ViewModel() {

    private val _canvases = MutableLiveData<List<Canvas>>()
    val canvases: LiveData<List<Canvas>>
        get() = _canvases

    // TODO: (3) Implement navigation for when image is clicked on
    private val _navigateToDetails = MutableLiveData<Boolean>()
    val navigateToDetails: LiveData<Boolean>
        get() = _navigateToDetails

    init {
        retrieveImagesFromStorage()
        Timber.i("CanvasViewModel Initialized")

    }

    fun onNavigateToDetails() {
        _navigateToDetails.value = true
    }

    fun doneNavigatingToDetails() {
        _navigateToDetails.value = false
    }


    private fun retrieveImagesFromStorage() {
        viewModelScope.launch {
            FirebaseUtils().retrieveProducts(object : FirebaseUtils.FirebaseServiceCallback{
                override fun onImageCallback(value: String) {
                    TODO("Not yet implemented")
                }

                override fun onProductListCallback(value: List<Canvas>) {
                    _canvases.value = value
                    Timber.i("${value.size}")
                }

            })
        }
    }

    // TODO: (1) Link realtime database and storage
//    private fun retrieveCanvasesFromDatabase(){
//        viewModelScope.launch {
//            val canvasList = mutableListOf<Canvas>()
//        }
//    }

}
