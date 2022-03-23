package com.tdr.app.kimikoscanvas.data

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) :
        Result<Nothing>()
}