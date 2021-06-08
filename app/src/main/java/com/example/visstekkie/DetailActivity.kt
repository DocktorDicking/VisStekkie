package com.example.visstekkie

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
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
    private val util: Util = Util()
    private val TAG = "DetailActivity"

    //selected stekkie
    private lateinit var stekkie: StekkieModel
    private var index: Int = -1

    //Views in the layout
    private lateinit var stekkieLoc: TextView
    private lateinit var stekkieImg: ImageView
    private lateinit var stekkieName: TextView
    private lateinit var stekkieDesc: TextView

    //Options used by Glide when inserting images into the view
    private lateinit var options: RequestOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        Log.d(TAG, "onCreate: has started.")

        //init views from layout
        stekkieImg = findViewById(R.id.stekkie_img)
        stekkieLoc = findViewById(R.id.stekkie_loc)
        stekkieName = findViewById(R.id.stekkie_name)
        stekkieDesc = findViewById(R.id.stekkie_desc)

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
            stekkie = intent.getSerializableExtra("stekkie") as StekkieModel
            index = intent.getIntExtra("index", -1)
            setStekkieData(stekkie)
        } else {
            Toast.makeText(this, "OOPS! Something went wrong ):", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Set's all the data in the detail layout for the selected stekkie
     */
    private fun setStekkieData(stekkie: StekkieModel) {
        Log.d(TAG, "setStekkieData: Setting details data for Stekkie")

        if (stekkie.imagePath != null) {
            Glide.with(stekkieImg.context).load(stekkie.getImagePathUri()).apply(options).into(stekkieImg)
        } else {
            Glide.with(stekkieImg.context).load(R.drawable.ph150).apply(options).into(stekkieImg)
        }

        stekkieName.text = stekkie.name
        stekkieLoc.text = stekkie.getLocationName(stekkieLoc.context)
        stekkieDesc.text = stekkie.description
    }

    fun closeStekkieDetail(view: View) {
        finish()
    }

    /**
     * PT function. Will trigger the app to open the addActivity result and load the existing model.
     */
    fun editStekkie(view: View) {
        returnResult(util.ACT_UPDATE)
    }

    /**
     * Deletes a stekkie based on the index of the stekkie.
     */
    fun deleteStekkie(view: View) {
        //TODO delete image file on local storage to clear space.

        //delete confirmation
        val builder = AlertDialog.Builder(stekkieName.context)
        builder.setTitle("Stekkie vernietigen?")
        builder.setMessage("Je staat op het punt dit stekkie te vernietigen! (verwijderen)\n" +
                "Weet je zeker dat je dit stekkie wilt verwijderen?")
        builder.setPositiveButton("Stekkie verwijderen!") { dialog, which -> returnResult(util.ACT_DELETE) }
        builder.setNegativeButton("Terug") { dialog, which -> dialog.dismiss() }
        builder.show()
    }

    private fun returnResult(action: Int) { //nested to reuse
        val intent = Intent()
        intent.putExtra("action", action)
        intent.putExtra("index", index)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}