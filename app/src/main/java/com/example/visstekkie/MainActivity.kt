package com.example.visstekkie

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.ActivityChooserView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), OnItemClickListener {

    //Test data
    private val modelArray: ArrayList<StekkieModel> = createTestStekkies()

    //Init the stekkie adapter to populate the recyclerview
    private val stekkieAdapter = StekkieAdapter(modelArray, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val stekkieRv: RecyclerView = findViewById(R.id.stekkie_rv)

        //Creating a default layout manager for the recyclerview
        val llm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //Binding the llm and adapeter to the recyclerview
        stekkieRv.layoutManager = llm
        stekkieRv.adapter = stekkieAdapter

        //TODO check for all permissions on start.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 111)
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_NETWORK_STATE), 111)
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 111)
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 111)
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 111)
        }
    }

    /**
     *
     */
    fun createStekkie(view: View) {
        val intent = Intent(this@MainActivity, AddStekkieActivity::class.java)
        startActivity(intent)

//        stekkieAdapter.notifyItemInserted(index)
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this, "Item $position clicked", Toast.LENGTH_SHORT).show()

        val stekkie = modelArray[position]
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra("stekkie", stekkie)
        startActivity(intent)
    }

    /**2
     * Test stekkies
     */
    fun createTestStekkies(): ArrayList<StekkieModel> {
        //TODO Fetch stekkies from firebase or something else that haves stekkies...
        val spot1latitude = 52.223718
        val spot1longitude = 4.667337

        val spot2latitude = 52.420659
        val spot2longitude = 6.316734

        val modelArray: ArrayList<StekkieModel> = ArrayList()
        modelArray.add(StekkieModel("Spot1", "A lot of Carp and Bream.", R.drawable.ph150, spot1latitude, spot1longitude))
        modelArray.add(StekkieModel("Spot2", "Catched a bunch or Roach here.", R.drawable.ph150, spot2latitude, spot2longitude))
        modelArray.add(StekkieModel("Spot3", "Bream hotspot!!", R.drawable.ph150, spot2latitude, spot2longitude))
        modelArray.add(StekkieModel("Spot4", "Nice and quite, have not fished here yet.", R.drawable.ph150, spot1latitude, spot1longitude))
        modelArray.add(StekkieModel("Spot5", "This spot looks good due to vegitation.", R.drawable.ph150, spot2latitude, spot2longitude))
        modelArray.add(StekkieModel("Spot6", "Catched 2 Carps here!", R.drawable.ph150, spot2latitude, spot2longitude))

        return modelArray
    }
}