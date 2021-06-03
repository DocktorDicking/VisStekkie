package com.example.visstekkie

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import java.io.File
import java.io.Serializable

class StekkieModel(
    var name: String?,
    var description: String?,
    var image: Int?, //TODO change this to string. We want to save Uri.toString() instead.
    var imagePath: String?,
    var latitude: Double,
    var longitude: Double
) : Serializable
{

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
    //TODO think about a way to save this (toString?) to a database.
}