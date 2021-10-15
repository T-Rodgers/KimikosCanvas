package com.tdr.app.kimikoscanvas.canvas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch
import timber.log.Timber

enum class ImageFolderSelector(val value: String) {
    PATTENS("images/pattens"),
    RODGERS("images/rodgers"),
    LANDSCAPES("images/landscapes"),
    MYERS("images/myers")
}

class CanvasViewModel() : ViewModel() {

    private val _canvases = MutableLiveData<List<Canvas>>()
    val canvases: LiveData<List<Canvas>>
        get() = _canvases

    // TODO (2) Implement navigation for when image is clicked on
    private val _navigateToDetails = MutableLiveData<Boolean>()
    val navigateToDetails: LiveData<Boolean>
        get() = _navigateToDetails

    init {
        retrieveCanvasesFromStorage()
    }

    fun doneNavigatingToDetails() {
        _navigateToDetails.value = false
    }

    // TODO (1) Add more photos to folder and implement navigation for each photoshoot!
    private fun retrieveCanvasesFromStorage() {
        viewModelScope.launch {
            val canvasList = mutableListOf<Canvas>()
            val storage = Firebase.storage
            val imagesRef =
                storage.reference.child(loadRandomImages())
            Timber.i(loadRandomImages())
            imagesRef.listAll().addOnSuccessListener {

                it.items.forEach { item ->
                    item.downloadUrl.addOnSuccessListener { uri ->
                        canvasList.add(Canvas(uri.toString(), item.name))
                    }.addOnCompleteListener { _canvases.value = canvasList }
                }
            }.addOnFailureListener {
                Timber.e("Error retrieving list of images")
            }
            Timber.i("${canvasList.size}")
        }
    }

    private fun loadRandomImages(): String {
        return ImageFolderSelector.values().toList().shuffled().first().value

    }
}
