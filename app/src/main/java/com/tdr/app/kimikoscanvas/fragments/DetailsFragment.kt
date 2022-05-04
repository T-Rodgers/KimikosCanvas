package com.tdr.app.kimikoscanvas.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import com.google.maps.android.SphericalUtil
import com.tdr.app.kimikoscanvas.R
import com.tdr.app.kimikoscanvas.canvas.DetailsViewModel
import com.tdr.app.kimikoscanvas.data.Canvas
import com.tdr.app.kimikoscanvas.databinding.DetailsFragmentBinding
import com.tdr.app.kimikoscanvas.utils.convertMetersToMiles
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

@ExperimentalCoroutinesApi
class DetailsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {

    private lateinit var binding: DetailsFragmentBinding
    private lateinit var map: GoogleMap
    private lateinit var canvas: Canvas
    private lateinit var marker: Marker
    private lateinit var userLatLong: LatLng

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    private val _viewModel: DetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.details_fragment, container, false)

        canvas = DetailsFragmentArgs.fromBundle(requireArguments()).selectedCanvas
        binding.canvas = canvas
        binding.lifecycleOwner = viewLifecycleOwner

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    getUserLocation()
                } else {
                    Timber.i("registerForActivityResult")

                    showLocationDialog()
                }
            }

        setHasOptionsMenu(true)

        observeConnection()

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {

        _viewModel.checkConnectionStatus()
        map = googleMap
        getUserLocation()
        showCanvasLocationOnMap()
    }

    override fun onMyLocationButtonClick(): Boolean {
        // Clear map and go to user's location
        map.clear()

        return false
    }

    private fun showDistanceInfo() {

        try {
            val distance = SphericalUtil.computeDistanceBetween(
                LatLng(canvas.latitude!!, canvas.longitude!!),
                userLatLong
            )

            val message =
                when (val convertedDistance = convertMetersToMiles(distance)) {
                    in 0..500 -> "You're not far! Only ${convertedDistance}mi away!"
                    in 501..1400 -> "Road trip! Your about ${convertedDistance}mi from here!"
                    else -> "Plan a vacation! This place is ${convertedDistance}mi from you!"
                }
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()

        } catch (e: UninitializedPropertyAccessException) {
            Timber.i(e.message)
            isPermissionGranted()
        }

    }

    @SuppressLint("MissingPermission")
    private fun getUserLocation() {

        Timber.i("Get Location Called")
        if (checkPermissions()) {
            map.isMyLocationEnabled = true
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    location?.let {
                        userLatLong = LatLng(it.latitude, it.longitude)
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLong, 15f))

                    }
                }
        }
    }

    private fun checkPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }


    @SuppressLint("MissingPermission")
    private fun isPermissionGranted() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                Timber.i("permission granted")
                // You can use the API that requires the permission.
                getUserLocation()
            }


            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                Timber.i("Show Rationale")

                showLocationDialog()
            }

            else -> {
                Timber.i("launch permission")
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
        }
    }

    private fun showCanvasLocationOnMap() {
        map.clear()
        val latLng = LatLng(canvas.latitude!!, canvas.longitude!!)
        val snippet = "${canvas.latitude}, ${canvas.longitude}"

        val cameraPosition = CameraPosition.Builder()
            .target(latLng)
            .zoom(15f)
            .build()
        val cu = CameraUpdateFactory.newCameraPosition(cameraPosition)
        map.animateCamera(cu)

        val bitmap =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_local_see_48, null)!!
                .toBitmap()
        marker = map.addMarker(
            MarkerOptions()
                .position(latLng)
                .snippet(snippet)
                .title(canvas.name)
                .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
        )!!

    }

    private fun observeConnection() {
        _viewModel.connectionStatus.observe(viewLifecycleOwner, Observer { connected ->
            when (connected) {
                false -> Snackbar.make(
                    requireView(),
                    "Network error, items may not be up to date.",
                    LENGTH_INDEFINITE
                )
                    .setAction(android.R.string.ok) {
                    }
                    .show()
                else -> return@Observer
            }
        })
    }

    private fun showLocationDialog() {
        val alertDialog: AlertDialog = requireActivity().let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setMessage(getString(R.string.location_dialog_message))
                setPositiveButton(
                    getString(android.R.string.ok)
                ) { dialog, _ ->
                    // User clicked OK button
                    Timber.i("Request Permission")

                    requestPermissionLauncher.launch(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )

                    dialog.dismiss()
                }
                setNegativeButton(
                    getString(R.string.dismiss_dialog)
                ) { dialog, _ ->
                    // User cancelled the dialog

                    dialog.dismiss()
                }

            }
            // Create the AlertDialog
            builder.create()
        }

        alertDialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.details_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_distance_info -> showDistanceInfo()
            R.id.action_show_on_map -> showCanvasLocationOnMap()
            else ->
                return super.onOptionsItemSelected(item)
        }
        return true
    }

}