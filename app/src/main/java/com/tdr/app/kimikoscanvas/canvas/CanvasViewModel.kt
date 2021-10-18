package com.tdr.app.kimikoscanvas.canvas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch
import timber.log.Timber

const val LANDSCAPES_FOLDER = "images/landscapes"

class CanvasViewModel() : ViewModel() {

    private val _canvases = MutableLiveData<List<Canvas>>()
    val canvases: LiveData<List<Canvas>>
        get() = _canvases

    // TODO (2) Implement navigation for when image is clicked on
    private val _navigateToDetails = MutableLiveData<Boolean>()
    val navigateToDetails: LiveData<Boolean>
        get() = _navigateToDetails

    init {
        retrieveImagesFromStorage()
    }

    fun doneNavigatingToDetails() {
        _navigateToDetails.value = false
    }


    private fun retrieveImagesFromStorage() {
        viewModelScope.launch {
            val canvasList = mutableListOf<Canvas>()
            val storage = Firebase.storage
            val reference =
                storage.reference.child(LANDSCAPES_FOLDER)
            reference.listAll().addOnSuccessListener {

                it.items.forEach { imageRef ->
                    canvasList.add(Canvas(imageRef.name, imageRef))
                }
            }.addOnFailureListener { Timber.i("Error retrieving images") }.addOnCompleteListener {
                _canvases.value = canvasList
            }
        }
    }

    // TODO: (1) Link realtime database and storage
//    private fun retrieveCanvasesFromDatabase(){
//        viewModelScope.launch {
//            val canvasList = mutableListOf<Canvas>()
//        }
//    }

}
