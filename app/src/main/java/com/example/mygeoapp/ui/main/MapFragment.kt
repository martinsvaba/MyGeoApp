package com.example.mygeoapp.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mygeoapp.R
import com.example.mygeoapp.databinding.FragmentMapBinding
import com.example.mygeoapp.dto.Locations
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class MapFragment : Fragment() {

    private lateinit var viewModel : MainViewModel
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var mMap : GoogleMap
    private var mapReady = false
    private lateinit var locations: List<Locations>

    private lateinit var firestore : FirebaseFirestore
    private var _locations: MutableLiveData<ArrayList<Locations>> = MutableLiveData<ArrayList<Locations>>()

    init {
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync{
            googleMap -> mMap = googleMap
            mapReady = true
            updateMap()
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        activity.let {
//            viewModel = ViewModelProvider(it!!).get(MainViewModel::class.java)
//        }

//        viewModel.locations.observe(viewLifecycleOwner, Observer {
//            locations -> this.locations = locations
//            updateMap()
//        })

            firestore.collection("locations").addSnapshotListener{
                    snapshot, e ->
                if (e != null){
                    Log.w("TAG", "Listen Failed", e)
                    return@addSnapshotListener
                }
                if (snapshot != null){
                    val documents = snapshot.documents
                    documents.forEach{
                        val location = it.toObject(Locations::class.java)
                        if (location != null){
                            val marker = LatLng(location.latitude.toDouble(), location.longitude.toDouble())
                            mMap.addMarker(MarkerOptions().position(marker).title(location.name))
                        }
                    }

                }
        }
    }

    private fun updateMap() {
//            locations.forEach {
//                if (it.latitude.isNotEmpty() && it.longitude.isNotEmpty()){
//                    val marker = LatLng(it.latitude.toDouble(), it.longitude.toDouble())
//                    mMap.addMarker(MarkerOptions().position(marker).title(it.toString()))
//                }
//            }

//        val marker = LatLng(41.9981, 21.4254)
//        mMap.addMarker(MarkerOptions().position(marker).title("Skopje"))
    }
}