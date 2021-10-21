package com.tdr.app.kimikoscanvas.utils

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.tdr.app.kimikoscanvas.canvas.Canvas
import com.tdr.app.kimikoscanvas.canvas.PRODUCTS_PATH
import timber.log.Timber

class FirebaseUtils {
    private val storage = Firebase.storage
    private val database = Firebase.database

//    private val imageRef =
//        storage.reference.child(IMAGES_PATH)
//    private val productRef = database.reference.child(PRODUCTS_PATH)

    // TODO: RetrieveProduct from Firebase RealtimeDatabase
    fun retrieveProducts(callback: FirebaseServiceCallback) {
        val canvasList = mutableListOf<Canvas>()
        database.reference.child(PRODUCTS_PATH).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children) {
                    val canvas = item.getValue<Canvas>()
                    canvasList.add(canvas!!)
                }
                callback.onProductListCallback(canvasList)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        Timber.i("${canvasList.size}")
    }

    interface FirebaseServiceCallback {
        fun onImageCallback(value: String)
        fun onProductListCallback(value: List<Canvas>)

    }
}