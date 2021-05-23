package com.example.visstekkie

import android.location.Location

class StekkieModel(
    var name: String,
    var description: String,
    var image: Int,
    var location: Location
)
{

    //TODO think about a way to save this (toString?) to a database.
}