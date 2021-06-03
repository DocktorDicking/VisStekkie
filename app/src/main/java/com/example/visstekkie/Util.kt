package com.example.visstekkie

import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri

class Util {

    /**
     * Checks if device haves an active internet connection by looking into activeNetwork.
     * Need to use deprecated methods here because i am using an old android phone for this project.
     */
    fun hasInternetAccess(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        if (activeNetwork != null) {
            return when (activeNetwork.type) {
                ConnectivityManager.TYPE_WIFI -> true
                ConnectivityManager.TYPE_MOBILE -> true
                else -> false
            }
        }
        return false
    }

    /**
     * Get's the Url (absolute path) of a android.resource file.
     */
    fun getURLForResource(resourceId: Int): String {
        //TODO TEST BEFORE USE, MIGHT NOT WORK (:
        return Uri.parse("android.resource://" + BuildConfig.APPLICATION_ID + "/" + resourceId).toString()
    }
}