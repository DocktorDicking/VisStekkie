package com.example.visstekkie

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AddStekkieActivity : AppCompatActivity() {
    private val TAG = "AddStekkieActivity"
    private val newStekkie = StekkieModel("", "", 0, 0.0, 0.0)
    private lateinit var stekkieLoc: TextView
    private lateinit var stekkieImg: ImageView

    private val IMAGE_CAPTURE_CODE = 1001
    private var picBitMap: Bitmap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addstekkie)
        stekkieImg = findViewById(R.id.stekkie_img)
        stekkieLoc = findViewById(R.id.stekkie_loc)

        Log.d(TAG, "onCreate: has started.")
        setStekkieLocation()
//        getIncommingIntent()
    }

    /**
     * Because on some devices the orientation changes after taking a picture, we need
     * to save the picture when the activity is rebuild. On rebuild 'onRestoreInstanceState' is called.
     *
     * Values in a activity are put in outState "automatically" accept for ImageViews.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //Save bitmap (containing img) to outState
        outState.putParcelable("picBitMap", picBitMap)
    }

    /**
     * See doc on OnSaveInstanceState
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        //Check if picBitMap is set in SavedInstanceState
        if (savedInstanceState.get("picBitMap") != null) {
            picBitMap = savedInstanceState.get("picBitMap") as Bitmap?
            stekkieImg.setImageBitmap(picBitMap)
        }

        if (picBitMap != null) {
            stekkieImg.setImageBitmap(picBitMap)
        }
    }

    /**
     * Sets the location of the new stekkie when called.
     */
    private fun setStekkieLocation() {
        //Tap into GPS sensor
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providers: List<String> =  locationManager.getProviders(true)
        var location: Location? = null

        //Check all providers if they have a value for location
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

        //Set long and lat in newStekkie obj
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
     * Activates the intent to create a picture which also calls for functions to
     * set the image to the ImageView.
     */
    fun addPhoto(view: View) {
        //Open default camera intent to make a picture
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    /**
     * Get's called by Android when user takes picture.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //called when image was captured from camera intent
        if (requestCode == IMAGE_CAPTURE_CODE){
            picBitMap = data?.getParcelableExtra("data")

            //set image captured to image view
            stekkieImg.setImageBitmap(picBitMap)
            Log.d(TAG, "onActivityResult: Image set.")
        }
    }

    /**
     *
     */
    fun createStekkie(view: View) {
        //TODO
    }


}