package com.example.visstekkie

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {
    private val TAG = "DetailActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        Log.d(TAG, "onCreate: has started.")

        getIncommingIntent()
    }

    /**
     * Check incoming intent (stekkie) on intent extra's.
     */
    private fun getIncommingIntent() {
        Log.d(TAG, "getIncommingIntent: checking for incoming intents.")
        if (intent.hasExtra("stekkieImg")
                && intent.hasExtra("stekkieName")
                && intent.hasExtra("stekkieDsc")
                && intent.hasExtra("stekkieLong")
                && intent.hasExtra("stekkieLat")) {

            Log.d(TAG, "getIncommingIntent: found intent extras.")

            val stekkieLoc = Location("Dummy")
            stekkieLoc.longitude = intent.getDoubleExtra("stekkieLong", 0.0)
            stekkieLoc.latitude = intent.getDoubleExtra("stekkieLat", 0.0)

            val stekkie = StekkieModel(
                    intent.getStringExtra("stekkieName"),
                    intent.getStringExtra("stekkieDsc"),
                    intent.getIntExtra("stekkieImg", 0),
                    stekkieLoc
            )

            setStekkieData(stekkie)
        } else {
            Toast.makeText(this, "OOPS! Something went wrong ):", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setStekkieData(stekkie: StekkieModel) {
        Log.d(TAG, "setStekkieData: Setting details data for Stekkie")

        val image: ImageView = findViewById(R.id.stekkie_img)
        stekkie.image?.let { image.setImageResource(it) }

        val name: TextView = findViewById(R.id.stekkie_name)
        name.text = stekkie.name

        val description: TextView = findViewById(R.id.stekkie_desc)
        description.text = stekkie.description
    }

    fun closeStekkieDetail(view: View) {}
    fun editStekkie(view: View) {}
    fun deleteStekkie(view: View) {}

}