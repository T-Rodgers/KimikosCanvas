package com.tdr.app.kimikoscanvas.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

class LoginViewModel: ViewModel() {
    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED
    }

    private val _authStatus = MutableLiveData<Event<String?>>()
    val authStatus : LiveData<Event<String?>>
    get() = _authStatus

    // state is mapped to whether Firebase user exists or is null
    val authenticationState = FirebaseUserLiveData().map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }

    fun onSignOut() {
        _authStatus.value = Event("Logging Out...")
    }
}