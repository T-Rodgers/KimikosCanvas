package com.tdr.data.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber


@ExperimentalCoroutinesApi
class CanvasRepositoryImpl constructor(private val firebaseDatabase: FirebaseDatabase) :
    CanvasRepository {

    companion object {
        const val CANVASES_REFERENCE = "canvases"
    }

    override fun getCanvasesFromFirebase() = callbackFlow<Result<List<Canvas>>> {

        val postListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                this@callbackFlow.trySendBlocking(Result.failure(error.toException()))
                    .onSuccess { channel.close() }
                    .onFailure {
                        it?.printStackTrace()
                        Timber.i("On Cancelled Failed")
                    }
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.map { dataSnapshot ->
                    dataSnapshot.getValue<Canvas>()
                }

                this@callbackFlow.trySendBlocking(
                    Result.success(
                        items.filterNotNull().sortedBy { it.id })
                        .onSuccess { Timber.i("Success") }
                        .onFailure {
                            it.printStackTrace()
                            Timber.i("Result is no good")
                        }
                )
            }
        }

        firebaseDatabase.getReference(CANVASES_REFERENCE)
            .addValueEventListener(postListener)

        awaitClose {
            Timber.i("Removing Listener")
            firebaseDatabase.getReference(CANVASES_REFERENCE)
                .removeEventListener(postListener)
        }
    }
}