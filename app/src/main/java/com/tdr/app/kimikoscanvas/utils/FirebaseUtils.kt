package com.tdr.app.kimikoscanvas.utils

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseUtils {
    private val database = Firebase.database
//    private lateinit var listener: ValueEventListener


    /**
     * Allow us to save items to local database for offline mode.
     */
    fun setPersistenceEnabled() {
        Firebase.database.setPersistenceEnabled(true)
    }

//    /**
//     * Retrieves list of canvases from database reference.
//     */
//    fun retrieveCanvases(callback: FirebaseServiceCallback) {
//        database.reference.child(CANVASES_REFERENCE).orderByKey()
//            .addValueEventListener(createListener(callback))
//    }
//
//    private fun createListener(callback: FirebaseServiceCallback): ValueEventListener {
//        val canvasList = mutableListOf<com.tdr.data.firebase.Canvas>()
//        listener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for (item in snapshot.children) {
//                    val canvas = item.getValue<com.tdr.data.firebase.Canvas>()
//                    canvas?.let { canvasList.add(it) }
//                    callback.onProductListCallback(canvasList)
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Timber.e(error.message)
//            }
//        }
//        return listener
//    }
//
//    /**
//     * Closes event listener that was used to retrieve any changes to database
//     */
//    fun removeListener() {
//        if (this::listener.isInitialized) {
//            database.reference.child(CANVASES_REFERENCE).removeEventListener(listener)
//        }
//        Timber.i(this::listener.isInitialized.toString())
//    }
//
//
//    /**
//     * Call back that waits until products are added to canvasList. Used to retrieve values and
//     * use them in our viewModel
//     */
//    interface FirebaseServiceCallback {
//        fun onProductListCallback(value: List<com.tdr.data.firebase.Canvas>)
//
//    }
}