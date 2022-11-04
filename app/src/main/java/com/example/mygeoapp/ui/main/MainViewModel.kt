package com.example.mygeoapp.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygeoapp.dto.Locations
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class MainViewModel : ViewModel() {
    private var firestore : FirebaseFirestore
    private var _locations: MutableLiveData<ArrayList<Locations>> = MutableLiveData<ArrayList<Locations>>()

    init {
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
        listenToLocations()
    }

    private fun listenToLocations() {
        firestore.collection("locations").addSnapshotListener{
            snapshot, e ->
            if (e != null){
                Log.w("LISTEN", "Listen Failed", e)
                return@addSnapshotListener
            }
            if (snapshot != null){
                val allLocations = ArrayList<Locations>()
                val documents = snapshot.documents
                documents.forEach{
                    val location = it.toObject(Locations::class.java)
                    if (location != null){
                        allLocations.add(location)
                    }
                }
                _locations.value = allLocations
            }
        }
    }
    internal var locations: MutableLiveData<ArrayList<Locations>>
    get() {return _locations}
    set(value) {_locations = value}
}