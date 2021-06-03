package com.example.visstekkie

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
        if (intent.hasExtra("stekkie")) {
            val stekkie = intent.getSerializableExtra("stekkie") as StekkieModel
            setStekkieData(stekkie)
        } else {
            Toast.makeText(this, "OOPS! Something went wrong ):", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setStekkieData(stekkie: StekkieModel) {
        Log.d(TAG, "setStekkieData: Setting details data for Stekkie")

        val image: ImageView = findViewById(R.id.stekkie_img)
        if (stekkie.imagePath != null) {
            image.setImageURI(stekkie.getImagePathUri())
        } else {
            image.setImageResource(R.drawable.ph150)
        }

        val name: TextView = findViewById(R.id.stekkie_name)
        name.text = stekkie.name

        val location: TextView = findViewById(R.id.stekkie_loc)
        location.text = stekkie.getLocationName(location.context)

        val description: TextView = findViewById(R.id.stekkie_desc)
        description.text = stekkie.description
    }

    fun closeStekkieDetail(view: View) {
        finish()
    }
    fun editStekkie(view: View) {}
    fun deleteStekkie(view: View) {}
}