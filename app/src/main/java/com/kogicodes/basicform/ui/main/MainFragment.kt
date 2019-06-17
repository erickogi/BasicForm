package com.kogicodes.basicform.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.loader.app.LoaderManager
import com.bumptech.glide.Glide
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder
import com.google.android.gms.vision.barcode.Barcode
import com.kogicodes.basicform.MainActivity
import com.kogicodes.basicform.PrefrenceManager
import com.kogicodes.basicform.R
import com.kogicodes.basicform.model.Data
import kotlinx.android.synthetic.main.main_fragment.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment(), View.OnClickListener{
    private  val REQUEST_PHOTO = 0x3000

    var uri: Uri?=null
    override fun onClick(p0: View?) {
        if(p0==edt_avatar||p0==avatar){
            if (checkPerrmission()) {
                selectAvatar()
            } else {
                requestPermissions()
            }
        }
    }
    private fun selectAvatar() {
        if (checkLocationPerrmission()) {
            launchCamera()
        } else {
            requestLocationPermissions()
        }
    }
    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)


        edt_qr.setOnClickListener{
            scan()
        }

        edt_avatar.setOnClickListener(this)
        avatar.setOnClickListener(this)

        btn_save.setOnClickListener {

            if(Utils.isValidName(edt_first_name)&&Utils.isValidName(edt_last_name)&&Utils.isValidName(edt_qr)&&Utils.isValidName(edt_id)){


                viewModel.add(Data(firstName = edt_first_name.text.toString(),lastName = edt_last_name.text.toString(),
                    idNumber = edt_id.text.toString(),uri = uri,qr = edt_qr.text.toString(),lat = PrefrenceManager(context!!).fetGps().lat,lon = PrefrenceManager(context!!).fetGps().lon))

                Toast.makeText(context,"Added go back to view",Toast.LENGTH_LONG).show()

                (activity as MainActivity).onBackPressed()
            }
        }



    }
    val CAMERA_REQUEST_CODE=0

    @SuppressLint("ShowToast")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Utils().loadImage(context!!,uri,avatar)

        super.onActivityResult(requestCode, resultCode, data)



    }
    fun scan() {
        if (checkPerrmission()) {
            startScan()
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("WrongConstant")
    private fun checkPerrmission(): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || PermissionChecker.checkSelfPermission(
            context!!,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }
    @SuppressLint("WrongConstant")
    private fun checkLocationPerrmission(): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || PermissionChecker.checkSelfPermission(
            context!!,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(activity as MainActivity, arrayOf(Manifest.permission.CAMERA), 100)
        ActivityCompat.requestPermissions(activity as MainActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 101)
    }
    private fun requestLocationPermissions() {
        ActivityCompat.requestPermissions(activity as MainActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 101)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startScan()
            } else {
                permisionDeneied()
            }


        }
        if (requestCode == 101 && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchCamera()
            } else {
                permisionDeneied()
            }


        }



    }


    private fun permisionDeneied() {
        val dialog = Dialog(context!!)
        dialog.setCancelable(false)
        dialog.setTitle("Permission Denied")
        dialog.show()

    }

    private fun startScan() {

        val materialBarcodeScanner = MaterialBarcodeScannerBuilder()
            .withActivity(activity as MainActivity)
            .withBackfacingCamera()
            .withCenterTracker()
            .withOnly2DScanning()
           .withBarcodeFormats(Barcode.AZTEC or Barcode.EAN_13 or Barcode.CODE_93)

            .withEnableAutoFocus(true)
            .withBleepEnabled(true)
            .withCenterTracker()
            .withBackfacingCamera()
            .withText("Scanning...")
            .withResultListener { barcode ->
                edt_qr.setText("" + barcode.rawValue)
            }
            .build()
        materialBarcodeScanner.startScan()

    }
    private fun launchCamera() {


        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(requireContext().packageManager) == null) return
        val photoFile = try {
            createImageFile()
        } catch (e: Exception) {
            return
        }
        val photoUri = FileProvider.getUriForFile(requireContext(), getString(R.string.file_provider), photoFile)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        requireContext().packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
            .forEach { info ->
                val packageName = info.activityInfo.packageName
                requireContext().grantUriPermission(
                    packageName,
                    photoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
        startActivityForResult(intent, REQUEST_PHOTO)
    }

    @SuppressLint("SimpleDateFormat")
    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().time)
        val imageFileName = "IMG_" + timeStamp + "_"
        val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
        storageDir.mkdirs()
        val image = File.createTempFile(imageFileName, ".jpg", storageDir)

        val success = image.delete() //no need to create empty file; camera app will create it on success

        // Save a file: path for use with ACTION_VIEW intents
        uri = Uri.fromFile(image)
        Log.d("USNFX","jrk"+uri.toString())
        return image
    }



}
