package com.tdr.app.kimikoscanvas.canvas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CanvasViewModel() : ViewModel() {

    private lateinit var canvases: MutableList<Canvas>

    private val _canvasList = MutableLiveData<List<Canvas>>()
    val canvasList: LiveData<List<Canvas>>
        get() = _canvasList

    private val _navigateToDetails = MutableLiveData<Boolean>()
    val navigateToDetails : LiveData<Boolean>
    get() = _navigateToDetails

    init {
        getAllCanvases()
    }

    fun doneNavigatingToDetails() {
        _navigateToDetails.value = false
    }

    private fun getAllCanvases(): List<Canvas>? {
        canvases = mutableListOf<Canvas>(
            Canvas("Sunset_1", 10),
            Canvas("Sunset_2", 30),
            Canvas("Sunset_3", 35),
            Canvas("Sunrise_1", 200),
            Canvas("Sunrise_2", 5),
            Canvas("Sunrise_3", 20),
            Canvas("Public Market", 25),
            Canvas("La Jolla Shores Hotel", 100),
            Canvas("Coronado Island", 230),
            Canvas("Fort Worth Water Gardens", 100)
        )

        _canvasList.value = canvases

        return _canvasList.value
    }
}