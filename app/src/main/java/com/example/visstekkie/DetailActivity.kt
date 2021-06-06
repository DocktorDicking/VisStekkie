package com.example.visstekkie

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailActivity : AppCompatActivity() {
    private val TAG = "DetailActivity"

    //Options used by Glide when inserting images into the view
    private lateinit var options: RequestOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        Log.d(TAG, "onCreate: has started.")

        options = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ph150)
                .error(R.drawable.ph150)

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
            Glide.with(image.context).load(stekkie.getImagePathUri()).apply(options).into(image)
        } else {
            Glide.with(image.context).load(R.drawable.ph150).apply(options).into(image)
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