package com.example.visstekkie

import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddStekkieActivity : AppCompatActivity() {
    private val TAG = "AddStekkieActivity"
    private val newStekkie = StekkieModel("", "", 0, 0.0, 0.0)
    private lateinit var stekkieLoc: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addstekkie)
        stekkieLoc = findViewById(R.id.stekkie_loc)
        Log.d(TAG, "onCreate: has started.")
        setStekkieLocation()

//        getIncommingIntent()
    }

    /**
     * Sets the location of the new stekkie when called.
     */
    private fun setStekkieLocation() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providers: List<String> =  locationManager.getProviders(true)
        var location: Location? = null
        for (provider in providers) {
            try {
                location = locationManager.getLastKnownLocation(provider)
                if (location != null) {
                    break
                }
            } catch (ex: SecurityException) {
                Log.d(TAG, "setStekkieLocation: Security exception thrown")
            }
        }

        if (location != null) {
            newStekkie.latitude = location.latitude
            newStekkie.longitude = location.longitude
            stekkieLoc.text = newStekkie.getLocationName(stekkieLoc.context)
        }
    }


    /**
     * Closes this intent
     */
    fun cancelStekkie(view: View) {
        finish()
    }

    /**
     *
     */
    fun addPhoto(view: View) {

    }

    /**
     *
     */
    fun createStekkie(view: View) {

    }


}