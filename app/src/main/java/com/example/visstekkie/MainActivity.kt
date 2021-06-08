package com.example.visstekkie

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), OnItemClickListener {
    private val util: Util = Util()

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

        //Binding the llm and adapter to the recyclerview
        stekkieRv.layoutManager = llm
        stekkieRv.adapter = stekkieAdapter

        //TODO test if this goes well on diffrent SDK versions. (Emulator)
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
     * Created the addStekkie intent and starts it using method 'startActivityForResult'
     */
    fun createStekkie(view: View) {
        val intent = Intent(this@MainActivity, AddStekkieActivity::class.java)
        intent.putExtra("action", util.ACT_CREATE)
        startActivityForResult(intent, util.REQ_CREATE_STEKKIE)
    }

    /**
     * Will open stekkie details.
     */
    override fun onItemClick(index: Int) {
        val stekkie = modelArray[index]
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra("stekkie", stekkie)
        intent.putExtra("index", index)
        startActivityForResult(intent, util.REQ_DETAIL_ACTIVITY)
    }

    /**
     * Handles activity results, such as the result for createStekkie method.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Check if requestCode is the code for createStekkie
        if (requestCode == util.REQ_CREATE_STEKKIE && resultCode == Activity.RESULT_OK) {
            //Get stekkie from incomming data, add stekkie to arraylist and notify adapter.
            val newStekkie: StekkieModel = data?.getSerializableExtra("data") as StekkieModel

            //TODO
            //sync with database

            modelArray.add(newStekkie)
            stekkieAdapter.notifyItemInserted(modelArray.indexOf(newStekkie))
            Toast.makeText(this, "Stekkie: " + newStekkie.name + " toegevoegd!", Toast.LENGTH_SHORT).show()
        }
        if (requestCode == util.REQ_UPDATE_STEKKIE && resultCode == Activity.RESULT_OK) {
            //Get stekkie from incomming data, add stekkie to arraylist and notify adapter.
            val newStekkie: StekkieModel = data?.getSerializableExtra("data") as StekkieModel
            val index = data.getIntExtra("index", -1)
            modelArray[index] = newStekkie
            stekkieAdapter.notifyDataSetChanged()

            //use index to replace existing stekkie
            //sync with database.

            //TODO

        }

        if (requestCode == util.REQ_DETAIL_ACTIVITY && resultCode == Activity.RESULT_OK) {
            if (data?.getIntExtra("action",-1) == util.ACT_DELETE) {
                val index: Int = data?.getIntExtra("index", -1) as Int

                if ((modelArray.size - 1) >= index) {
                    val stekkieName: String? = modelArray.get(index).name
                    modelArray.removeAt(index)
                    stekkieAdapter.notifyItemRemoved(index)
                    Toast.makeText(this, "Stekkie: " + stekkieName + " verwijderd!", Toast.LENGTH_SHORT).show()
                    //TODO
                    //sync with database
                }
            }
            if (data?.getIntExtra("action",-1) == util.ACT_UPDATE) {
                val index = data.getIntExtra("index",-1)

                if (index >= 0) {
                    val intent = Intent(this@MainActivity, AddStekkieActivity::class.java)
                    intent.putExtra("action", util.ACT_UPDATE)
                    intent.putExtra("index", index)
                    intent.putExtra("data", modelArray[index])
                    startActivityForResult(intent, util.REQ_UPDATE_STEKKIE)
                } else {
                    Toast.makeText(this, "Er is iets fout gegaan, stekkie kan niet " +
                            "worden geupdate. \n (INDEX > 0)", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * Test stekkies
     */
    fun createTestStekkies(): ArrayList<StekkieModel> {
        //TODO Fetch stekkies from firebase or something else that haves stekkies...
        val spot1latitude = 52.223718
        val spot1longitude = 4.667337

        val spot2latitude = 52.420659
        val spot2longitude = 6.316734

        val modelArray: ArrayList<StekkieModel> = ArrayList()
        modelArray.add(StekkieModel("Spot1", "A lot of Carp and Bream.", null, spot1latitude, spot1longitude))
        modelArray.add(StekkieModel("Spot2", "Catched a bunch or Roach here.", null, spot2latitude, spot2longitude))
        modelArray.add(StekkieModel("Spot3", "Bream hotspot!!", null, spot2latitude, spot2longitude))
        modelArray.add(StekkieModel("Spot4", "Nice and quite, have not fished here yet.", null, spot1latitude, spot1longitude))
        modelArray.add(StekkieModel("Spot5", "This spot looks good due to vegitation.", null, spot2latitude, spot2longitude))
        modelArray.add(StekkieModel("Spot6", "Catched 2 Carps here!", null, spot2latitude, spot2longitude))

        return modelArray
    }
}