package com.tdr.app.kimikoscanvas.canvas

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tdr.app.kimikoscanvas.data.CanvasDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalCoroutinesApi
class DetailsViewModel(app: Application, private val dataSource: CanvasDataSource) :
    AndroidViewModel(app) {

    private val _connectionStatus = MutableLiveData<Boolean?>()
    val connectionStatus: LiveData<Boolean?> = _connectionStatus

    fun checkConnectionStatus() {
        viewModelScope.launch {
            dataSource.getConnectionStatus().collect() {
                when {
                    it.isSuccess -> {
                        val isConnected = it.getOrNull()
                        _connectionStatus.value = isConnected
                        Timber.i("$isConnected")

                    }
                    it.isFailure -> {
                        it.exceptionOrNull()?.printStackTrace()
                    }
                }
            }
        }

    }
}