package com.example.visstekkie

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val stekkieRv: RecyclerView = findViewById(R.id.stekkie_rv)

        //Test data spot1 and spot2
        val spot1 = Location("dummy")
        spot1.latitude = 52.120675
        spot1.longitude = 5.832018

        val spot2 = Location("dummy")
        spot2.latitude = 52.420659
        spot2.longitude = 6.316734


        val modelArray: ArrayList<StekkieModel> = ArrayList()
        modelArray.add(StekkieModel("Spot1", "A lot of Carp and Bream.", R.drawable.ph150, spot1))
        modelArray.add(StekkieModel("Spot2", "Catched a bunch or Roach here.", R.drawable.ph150, spot2))
        modelArray.add(StekkieModel("Spot3", "Bream hotspot!!", R.drawable.ph150, spot2))
        modelArray.add(StekkieModel("Spot4", "Nice and quite, have not fished here yet.", R.drawable.ph150, spot2))
        modelArray.add(StekkieModel("Spot5", "This spot looks good due to vegitation.", R.drawable.ph150, spot2))
        modelArray.add(StekkieModel("Spot6", "Catched 2 Carps here!", R.drawable.ph150, spot2))

        //Init the stekkie adapter to populate the recyclerview
        val stekkieAdapter = StekkieAdapter(modelArray, this)

        //Creating a default layout manager for the recyclerview
        val llm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //Binding the llm and adapeter to the recyclerview
        stekkieRv.layoutManager = llm
        stekkieRv.adapter = stekkieAdapter
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this, "Item $position clicked", Toast.LENGTH_SHORT).show()
    }
}