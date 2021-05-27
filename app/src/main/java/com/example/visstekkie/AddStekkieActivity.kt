package com.example.visstekkie

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class AddStekkieActivity : AppCompatActivity() {
    private val TAG = "AddStekkieActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addstekkie)
        Log.d(TAG, "onCreate: has started.")

//        getIncommingIntent()
    }

    fun cancelStekkie(view: View) {}
    fun addPhoto(view: View) {}
    fun createStekkie(view: View) {}


}