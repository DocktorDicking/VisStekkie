package com.example.visstekkie

import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
    }

    /**
     *
     */
    fun createStekkie(view: View) {
        //TODO change to actually using a form and the camera

        //new index for the new stekkie
        val index = modelArray.size
        val newStekkie = StekkieModel(
            "Spot${modelArray.size}",
            "Test description",            R.drawable.ph150,
            Location("dummy")
        )

        modelArray.add(index, newStekkie)
        stekkieAdapter.notifyItemInserted(index)
        Toast.makeText(this, "New Stekkie has been added", Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this, "Item $position clicked", Toast.LENGTH_SHORT).show()

        var stekkie = modelArray[position]
        var intent: Intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra("stekkieImg", stekkie.image)
        intent.putExtra("stekkieName", stekkie.name)
        intent.putExtra("stekkieDsc", stekkie.description)
        intent.putExtra("stekkieLong", stekkie.location?.longitude)
        intent.putExtra("stekkieLat", stekkie.location?.latitude)
        startActivity(intent)
    }

    /**2
     * Test stekkies
     */
    fun createTestStekkies(): ArrayList<StekkieModel> {
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

        return modelArray
    }
}