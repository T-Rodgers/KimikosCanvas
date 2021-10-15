package com.tdr.app.kimikoscanvas.canvas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch
import timber.log.Timber

class CanvasViewModel() : ViewModel() {

    private val canvasList = mutableListOf<Canvas>()

    private val _canvases = MutableLiveData<List<Canvas>>()
    val canvases: LiveData<List<Canvas>>
        get() = _canvases

    // TODO (2) Implement navigation for when image is clicked on
    private val _navigateToDetails = MutableLiveData<Boolean>()
    val navigateToDetails: LiveData<Boolean>
        get() = _navigateToDetails

    init {
        getAllCanvases()
    }

    fun doneNavigatingToDetails() {
        _navigateToDetails.value = false
    }

    // TODO (1) Add more photos to folder and implement navigation for each photoshoot!
    private fun getAllCanvases() {
        viewModelScope.launch {
            val storage = Firebase.storage
            val imagesRef =
                storage.reference.child("images/landscapes")
            imagesRef.listAll().addOnSuccessListener {
                it.items.forEach { item ->
                    item.downloadUrl.addOnSuccessListener { uri ->

                        canvasList.add(Canvas(uri.toString(), item.name))
                        _canvases.value = canvasList
                    }.addOnFailureListener {
                        Timber.i("Error adding images to list")
                    }
                }
            }.addOnFailureListener {
                Timber.e("Error retrieving list of images")
            }
        }
    }
}