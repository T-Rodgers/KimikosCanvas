package com.tdr.app.kimikoscanvas.utils

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.tdr.app.kimikoscanvas.canvas.LANDSCAPES_FOLDER
import timber.log.Timber

class FirebaseUtils {
    private val storage = Firebase.storage
    private val reference =
        storage.reference.child(LANDSCAPES_FOLDER)

    fun retrieveImages(myCallBack: FirebaseServiceCallback){

        reference.listAll().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val canvasList = mutableListOf<StorageReference>()
                for (image in task.result!!.items) {
                    canvasList.add(image)

                    Timber.i("Items in Result: ${canvasList.size}")
                }
                myCallBack.onImageCallback(canvasList)
            }
        }
    }

    fun retrieveProducts() {

    }

    interface FirebaseServiceCallback {
        fun onImageCallback(value: List<StorageReference>)

    }
}