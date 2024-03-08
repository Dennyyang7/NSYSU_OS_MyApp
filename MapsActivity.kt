package com.example.mainactivity

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.mainactivity.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.PolylineOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivityMapsBinding.inflate(layoutInflater)
     setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        //顯示目前位置與目前位置的按鈕
        //加入標記
        val marker = MarkerOptions()
        marker.position(LatLng(22.628358202149208, 120.26471917058453))
        marker.title("中山大學")
        mMap.addMarker(marker)

        marker.position(LatLng(22.622365533877456, 120.2733196942133))
        marker.title("丹丹漢堡")
        mMap.addMarker(marker)

        marker.position(LatLng(22.622060563402003, 120.2736010684802))
        marker.title("西子灣捷運站1")
        mMap.addMarker(marker)

        marker.position(LatLng(22.62581065597063, 120.27396412671351))
        marker.title("壽山情人景觀台")
        mMap.addMarker(marker)

        marker.position(LatLng(22.622676591469805, 120.27437187617511))
        marker.title("臺灣銀行")
        mMap.addMarker(marker)

        marker.position(LatLng(22.62839011898437, 120.26473546113345))
        marker.title("麗文校園書局")
        mMap.addMarker(marker)
        //移動視角
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                LatLng(22.628358202149208, 120.26471917058453), 15f))
    }

}