package com.tdr.app.kimikoscanvas.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.tdr.app.kimikoscanvas.R
import com.tdr.app.kimikoscanvas.canvas.Canvas
import com.tdr.app.kimikoscanvas.databinding.DetailsFragmentBinding

class DetailsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding : DetailsFragmentBinding
    private lateinit var map : GoogleMap
    private lateinit var canvas: Canvas
    private lateinit var marker: Marker

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.details_fragment, container, false)

        canvas = DetailsFragmentArgs.fromBundle(requireArguments()).selectedImage
        binding.canvas = canvas

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val latLng = LatLng(canvas.latitude!!, canvas.longitude!!)
        val snippet = "${canvas.latitude}, ${canvas.longitude}"
        marker = map.addMarker(
            MarkerOptions()
                .position(latLng)
                .snippet(snippet)
                .title(canvas.name)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
        )!!

        val cameraPosition = CameraPosition.Builder()
            .target(latLng)
            .zoom(15f)
            .build()
        val cu = CameraUpdateFactory.newCameraPosition(cameraPosition)
        map.animateCamera(cu)

    }

}