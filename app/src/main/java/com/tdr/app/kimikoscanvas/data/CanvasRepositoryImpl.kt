package com.tdr.app.kimikoscanvas.data

import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber


@ExperimentalCoroutinesApi
class CanvasRepositoryImpl constructor(private val firebaseDatabase: FirebaseDatabase) :
    CanvasDataSource {

    companion object {
        const val CANVASES_REFERENCE = "canvases"
        const val CONNECTION_REFERENCE = ".info/connected"
    }

    override fun getCanvasesFromFirebase() = callbackFlow {


        val postListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                try {
                    this@callbackFlow.trySend(Result.failure(error.toException()))
                } catch (e: DatabaseException) {
                    Timber.i(e)
                }

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.map { dataSnapshot ->
                    dataSnapshot.getValue<Canvas>()
                }

                this@callbackFlow.trySend(
                    Result.success(
                        items.filterNotNull().sortedBy { it.id })
                        .onSuccess { Timber.i("Successfully retrieved values") }
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

    override fun getConnectionStatus(): Flow<Result<Boolean>> = callbackFlow {
        val postListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

                this@callbackFlow.trySendBlocking(Result.failure(error.toException()))

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val item = snapshot.getValue<Boolean>()

                this@callbackFlow.trySendBlocking(
                    Result.success(item!!)
                )
            }

        }

        firebaseDatabase.getReference(CONNECTION_REFERENCE)
            .addValueEventListener(postListener)

        awaitClose {
            Timber.i("Removing Listener")
            firebaseDatabase.getReference(CONNECTION_REFERENCE)
                .removeEventListener(postListener)
        }
    }
}