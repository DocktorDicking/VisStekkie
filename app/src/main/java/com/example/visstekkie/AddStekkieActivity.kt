package com.example.visstekkie

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AddStekkieActivity : AppCompatActivity() {
    private val TAG = "AddStekkieActivity"

    //new model
    private var newStekkie = StekkieModel(null, null,  null, 0.0, 0.0)

    //Views in the layout
    private lateinit var stekkieLoc: TextView
    private lateinit var stekkieImg: ImageView
    private lateinit var stekkieName: EditText
    private lateinit var stekkieDesc: EditText

    //Variables used by camera intent
    private val IMAGE_CAPTURE_CODE = 1001


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addstekkie)

        //init views from layout
        stekkieImg = findViewById(R.id.stekkie_img)
        stekkieLoc = findViewById(R.id.stekkie_loc)
        stekkieName = findViewById(R.id.stekkie_name)
        stekkieDesc = findViewById(R.id.stekkie_desc)

        setStekkieLocation()
        Log.d(TAG, "onCreate: has started, location has been set.")
    }

    /**
     * Because on some devices the orientation changes after taking a picture, we need
     * to save the picture when the activity is rebuild. On rebuild 'onRestoreInstanceState' is called.
     *
     * Values in a activity are put in outState "automatically" accept for ImageViews.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState: I know da wea")

        //Save model state
        outState.putSerializable("newStekkie", newStekkie)
    }

    /**
     * See doc on OnSaveInstanceState
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(TAG, "onRestoreInstanceState: Do you know da wae?")

        //Get the active model state
        if (savedInstanceState.get("newStekkie") != null) {
            newStekkie = savedInstanceState.get("newStekkie") as StekkieModel
            if (newStekkie.imagePath != null) {
                stekkieImg.setImageURI(newStekkie.getImagePathUri())
            }
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
     * Create a file to save the Image to (photo taken with camera). File is saved in External
     * storage which is private to this application.
     */
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        ).apply {
            //Get the absolute path and save it in the model
            newStekkie.imagePath = absolutePath
        }
    }


    /**
     * Activates the intent to create a picture which also calls for functions to
     * set the image to the ImageView.
     *
     * Source: Android documentation
     */
    fun addPhoto(view: View) {
        //Camera intent
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { cameraIntent ->
            // Ensure that there's a camera activity to handle the intent
            cameraIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    Log.d(TAG, "addPhoto: IOException thrown!")
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI = FileProvider.getUriForFile(
                            this,
                            "com.example.android.fileprovider",
                            it
                    )
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
                }
            }
        }

    }

    /**
     * Android goes into this method when 'startActivityForResult' is called.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //called when image was captured from camera intent
        if (requestCode == IMAGE_CAPTURE_CODE && resultCode == Activity.RESULT_OK){
            if (newStekkie.imagePath != null) {
                stekkieImg.setImageURI(newStekkie.getImagePathUri())
            }
            //TODO Image in view is sideways >.>

            //set image captured to image view
            Log.d(TAG, "onActivityResult: Image set.")
        }
    }

    /**
     * Creates a new StekkieModel and adds the data from addStekkieActivity to the model.
     */
    fun createStekkie(view: View) {
//        var newStekkie: StekkieModel = StekkieModel("", "", 0, 0.0,0.0)
        //TODO
    }


}