package com.example.visstekkie

import android.content.Context
import android.net.ConnectivityManager

class Util {
    val REQ_CREATE_STEKKIE = 123
    val REQ_UPDATE_STEKKIE = 321
    val REQ_DETAIL_ACTIVITY = 111

    val ACT_DELETE = 999
    val ACT_UPDATE = 201

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
}