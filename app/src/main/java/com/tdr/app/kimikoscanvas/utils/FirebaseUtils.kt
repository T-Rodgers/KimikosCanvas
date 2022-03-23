package com.tdr.app.kimikoscanvas.utils

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.tdr.app.kimikoscanvas.canvas.PRODUCTS_PATH
import com.tdr.app.kimikoscanvas.data.CanvasDTO
import timber.log.Timber

class FirebaseUtils {
    private val storage = Firebase.storage
    private val database = Firebase.database
    private lateinit var listener: ValueEventListener

    /**
     * Retrieves list of canvases from database reference.
     */
    fun retrieveCanvases(callback: FirebaseServiceCallback) {
        database.reference.child(PRODUCTS_PATH).orderByKey()
            .addValueEventListener(createListener(callback))
    }

    fun createListener(callback: FirebaseServiceCallback): ValueEventListener {
        val canvasList = mutableListOf<CanvasDTO>()
        listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children) {
                    val canvas = item.getValue<CanvasDTO>()
                    canvas?.let { canvasList.add(it) }
                    callback.onProductListCallback(canvasList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Timber.e(error.message)
            }

        }
        return listener
    }

    /**
     * Closes even listener that was used to retrieve any changes to database
     */
    fun removeListener() {
        if (this::listener.isInitialized) {
            database.reference.child(PRODUCTS_PATH).removeEventListener(listener)
        }
        Timber.i(this::listener.isInitialized.toString())
    }


    /**
     * Call back that waits until products are added to canvasList. Used to retrieve values and
     * use them in our viewModel
     */
    interface FirebaseServiceCallback {
        fun onProductListCallback(value: List<CanvasDTO>)

    }
}