package com.tdr.app.kimikoscanvas.canvas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CanvasViewModel() : ViewModel() {

    private val _canvases = MutableLiveData<List<Canvas>>()
    val canvases: LiveData<List<Canvas>>
        get() = _canvases

    private val _navigateToDetails = MutableLiveData<Boolean>()
    val navigateToDetails : LiveData<Boolean>
    get() = _navigateToDetails

    init {
        getAllCanvases()
    }

    fun doneNavigatingToDetails() {
        _navigateToDetails.value = false
    }

    private fun getAllCanvases() {
       val canvasList = mutableListOf<Canvas>(
            Canvas(1,"Sunset_1", 10),
            Canvas(2,"Sunset_2", 30),
            Canvas(3,"Sunset_3", 35),
            Canvas(4,"Sunrise_1", 200),
            Canvas(5,"Sunrise_2", 5),
            Canvas(6,"Sunrise_3", 20),
            Canvas(7,"Public Market", 25),
            Canvas(8,"La Jolla Shores Hotel", 100),
            Canvas(9,"Coronado Island", 230),
            Canvas(10,"Fort Worth Water Gardens", 100)
        )
        _canvases.value = canvasList
    }
}