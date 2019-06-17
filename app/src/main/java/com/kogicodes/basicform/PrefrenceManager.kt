package com.kogicodes.basicform

import android.content.Context
import android.content.SharedPreferences
import android.location.Location

class PrefrenceManager(internal var _context: Context) {

    internal var pref: SharedPreferences

    internal var editor: SharedPreferences.Editor

    internal var PRIVATE_MODE = 0


    companion object {

        private val LAT = "latitude"
        private val LON = "longituted"

        private val PREF_NAME = "basicform_prefrences"

    }

    init {
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }


    fun clearUser() {
        editor.clear()
        editor.commit()
    }

    fun setGps(location : Location) {
        editor.putString(LAT, location.latitude.toString())
        editor.putString(LON, location.longitude.toString())
        editor.commit()
    }


    fun fetGps(): LatLng {

        return LatLng(pref.getString(LAT,""),pref.getString(LON,""))
    }



}

class LatLng {
    var lat: String?=null
    var lon: String?=null

    constructor(lat: String?, lon: String?) {
        this.lat = lat
        this.lon = lon
    }
}
