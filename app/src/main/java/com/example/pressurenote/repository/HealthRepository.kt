package com.example.pressurenote.repository

import com.example.pressurenote.model.Health
import com.example.pressurenote.model.Indication
import com.example.pressurenote.utils.Utils.getDate
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class HealthRepository @Inject constructor(private val db: DatabaseReference) {
    suspend fun getHealth(): List<Health> {
        return suspendCoroutine { continuation ->
            db.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list: MutableList<Health> = mutableListOf()
                    snapshot.children.forEach { dateDB ->
                        val date = dateDB.key.toString()
                        val indications = mutableListOf<Indication>()
                        dateDB.children.forEach { child ->
                            child.getValue(Indication::class.java)?.let { indications.add(it) }
                        }
                        list.add(Health(date, indications.asReversed()))
                    }
                    continuation.resume(list)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }

    fun setIndications(indication: Indication) {
            db.child(getDate()).push().setValue(indication)
        }
    }