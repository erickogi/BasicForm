package com.kogicodes.basicform

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.agriclinic.common.listeners.OnViewItemClick
import com.agriclinic.common.utils.SimpleDialogModel
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.kogicodes.basicform.ui.main.ListFragment
import com.kogicodes.basicform.ui.main.MainFragment
import com.kogicodes.basicform.ui.main.Utils

class MainActivity : AppCompatActivity() {
    private val PERMISSIONS_REQUEST = 1
    private var googleApiClient: GoogleApiClient? = null


    private fun settings() {

        if (googleApiClient == null) {
            googleApiClient = GoogleApiClient.Builder(applicationContext).addApi(LocationServices.API).build()
            (googleApiClient as GoogleApiClient).connect()

            val locationRequest = LocationRequest.create()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            locationRequest.interval = (30 * 1000).toLong()
            locationRequest.fastestInterval = (5 * 1000).toLong()
            val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

            // **************************

            builder.setAlwaysShow(true) // this is the key ingredient


            val result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
            @Override
            fun onResult(@NonNull result: Result<LocationSettingsRequest>) {
//                                result.setResultCallback({ results: LocationSettingsResult ->
//
//                                    val status = results.status
//
//                                    val state = results.locationSettingsStates
//
//                                    when (status.statusCode) {
//
//                                        LocationSettingsStatusCodes.SUCCESS -> {
//                                        }
//
//                                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->
//
//
//                                            try {
//
//                                                status.startResolutionForResult(this@AuthActivity, 1000)
//
//                                            } catch (e: IntentSender.SendIntentException) {
//
//                                            }
//
//                                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
//                                        }
//                                    }
//
//                                } as ResultCallback<LocationSettingsResult>)

            }
        }
    }

    private fun startTrackerService() {
        startService(Intent(this, TrackerService::class.java))
        // finish();
    }

    private fun setUp() {
        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            settings()

        }

        // Check location permission is granted - if it is, start
        // the service, otherwise request the permission
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permission == PackageManager.PERMISSION_GRANTED) {
            startTrackerService()
        } else {

            Utils.simpleYesNoDialog(
                this@MainActivity,
                "Location Permission",
                "To make the experience of using this app better and increase your reach to many clients as possible, turn on your location settings by accepting in the next dialog\n\n\nPlease make sure you are registering at your workstation so that we can pick the correct gps details.",
                SimpleDialogModel("Okay", null, null),
                object : OnViewItemClick {
                    override fun onPositiveClick() {


                        ActivityCompat.requestPermissions(
                            this@MainActivity,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            PERMISSIONS_REQUEST
                        )
                    }

                    override fun onNegativeClick() {
                        ActivityCompat.requestPermissions(
                            this@MainActivity,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            PERMISSIONS_REQUEST
                        )
                    }

                    override fun onNeutral() {
                        ActivityCompat.requestPermissions(
                            this@MainActivity,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            PERMISSIONS_REQUEST
                        )
                    }

                })

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSIONS_REQUEST && grantResults.size == 1
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            // Start the service when the permission is granted
            startTrackerService()
        } else {
            //finish();
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setUp()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ListFragment.newInstance(),"list")
                .commitNow()
        }



    }


    fun add(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance()).addToBackStack("add")
            .commit()


    }

    override fun onBackPressed() {
        super.onBackPressed()
        val myFragment = supportFragmentManager.findFragmentByTag("list")// as ListFragment
        if (myFragment != null && myFragment!!.isVisible()) {
            ( myFragment as ListFragment).initview()
        }
    }
}
