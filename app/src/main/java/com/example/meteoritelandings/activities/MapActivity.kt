package com.example.meteoritelandings.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.meteoritelandings.Utils.Constants.SELECTED_METEORITE
import com.example.meteoritelandings.Utils.toSimpleString
import com.example.meteoritelandings.R
import com.example.meteoritelandings.adapters.InformationAdapter
import com.example.meteoritelandings.models.Meteorite
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var meteorite: Meteorite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val stringExtra = intent.getStringExtra(SELECTED_METEORITE)
        meteorite = Gson().fromJson<Meteorite>(stringExtra, Meteorite::class.java)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setInfoWindowAdapter(InformationAdapter(applicationContext))

        val coordinate = LatLng(meteorite.reclat, meteorite.reclong)
        map.addMarker(createMarkerOptions(coordinate))
                .showInfoWindow()
        map.moveCamera(CameraUpdateFactory.newLatLng(coordinate))
    }

    private fun createMarkerOptions(coordinate: LatLng) = MarkerOptions()
            .position(coordinate)
            .title(createTitle())
            .snippet(createSnippet())

    private fun createTitle() = meteorite.name

    private fun createSnippet() =
            "coordinates: ${meteorite.reclat}°, ${meteorite.reclong}° " + newLine() +
                    "nametype: ${meteorite.nametype} " + newLine() +
                    "recclass: ${meteorite.recclass} " + newLine() +
                    "mass (g): ${meteorite.mass}" + newLine() +
                    "fall: ${meteorite.fall}" + newLine() +
                    "date: ${meteorite.year.toSimpleString()}"


    private fun newLine() = System.getProperty("line.separator")
}
