package com.example.visstekkie

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import java.io.File
import java.io.Serializable

class StekkieModel : Serializable
{
    var id: Int = 0
    var name: String? = null
    var description: String? = null
    var imagePath: String? = null
    var latitude: Double = 0.0
    var longitude: Double = 0.0

    constructor(id: Int, name: String?, description: String?, imagePath: String?, latitude: Double, longitude: Double) {
        this.id = id
        this.name = name
        this.description = description
        this.imagePath = imagePath
        this.latitude = latitude
        this.longitude = longitude
    }

    constructor(name: String?, description: String?, imagePath: String?, latitude: Double, longitude: Double) {
        this.name = name
        this.description = description
        this.imagePath = imagePath
        this.latitude = latitude
        this.longitude = longitude
    }

    /**
     * Gets a name based on Location lat and long using android geoCoder
     */
    fun getLocationName(context: Context): String {
        val util = Util()
        val geocoder = Geocoder(context)

        if (util.hasInternetAccess(context)) {
            val list: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)
            if (list.isNotEmpty()) {
                val address = list[0]
                return address.locality
            }
            return "$latitude, $longitude"
        }
        return "$latitude, $longitude"
    }

    /**
     * Creates a Uri from the existing imagePath.
     */
    fun getImagePathUri(): Uri? {
        if (imagePath != null) {
            return Uri.fromFile(File(imagePath))
        }
        return null
    }
}