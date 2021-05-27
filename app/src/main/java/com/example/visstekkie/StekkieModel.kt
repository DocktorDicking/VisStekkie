package com.example.visstekkie

import android.content.Context
import android.location.Address
import android.location.Geocoder
import java.io.Serializable

class StekkieModel (
    var name: String?,
    var description: String?,
    var image: Int?,
    var latitude: Double,
    var longitude: Double
) : Serializable
{

    /**
     * Gets a name based on Location lat and long using android geoCoder
     */
    fun getLocationName(context: Context): String {
        val util: Util = Util()
        val geocoder = Geocoder(context)

        if (util.hasInternetAccess(context)) {
            val list: List<Address> =  geocoder.getFromLocation(latitude, longitude, 1)
            if (list.isNotEmpty()) {
                val address = list[0]
                return address.locality
            }
            return "$latitude, $longitude"
        }
        return "$latitude, $longitude"
    }
    //TODO think about a way to save this (toString?) to a database.
}