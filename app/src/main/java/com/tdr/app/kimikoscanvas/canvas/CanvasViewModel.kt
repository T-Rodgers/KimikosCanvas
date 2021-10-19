package com.tdr.app.kimikoscanvas.canvas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.StorageReference
import com.tdr.app.kimikoscanvas.utils.FirebaseUtils
import kotlinx.coroutines.launch
import timber.log.Timber

const val LANDSCAPES_FOLDER = "images/landscapes"

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
        // TODO: Grab image from storage to be used when retrieving database item
        FirebaseUtils().retrieveImages(object: FirebaseUtils.FirebaseServiceCallback{
            override fun onImageCallback(value: List<StorageReference>) {
                Timber.i(" Items Retrieved: ${value.size}")
            }

        })
        Timber.i("CanvasViewModel Initialized")
    }

    fun doneNavigatingToDetails() {
        _navigateToDetails.value = false
    }


    private fun retrieveImagesFromStorage() {
        viewModelScope.launch {

        }
    }

    // TODO: (1) Link realtime database and storage
//    private fun retrieveCanvasesFromDatabase(){
//        viewModelScope.launch {
//            val canvasList = mutableListOf<Canvas>()
//        }
//    }

}
