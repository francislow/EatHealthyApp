package com.eathealthyapp.is3261.eathealthyapp.activities

import android.Manifest
import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.content.pm.PackageManager
import android.os.Build
import android.support.annotation.RequiresApi
import android.widget.ImageButton
import com.eathealthyapp.is3261.eathealthyapp.R
import com.eathealthyapp.is3261.eathealthyapp.fragments.FragmentManager

class MainActivity : AppCompatActivity() {

    var allPermissionsGrantedFlag: Int = 0
    lateinit var fragmentManager: FragmentManager

    private val permissionList = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestAllPermission()

        fragmentManager = FragmentManager(this)
        fragmentManager.setup()

        val camBtn = findViewById<ImageButton>(R.id.cam_btn)
        camBtn.setOnClickListener {
            val camIntent = Intent(this, ActivityScanner::class.java)
            startActivity(camIntent)
        }
    }




    //----------------------------- Request for permission stuffs ---------------------------------
    private fun requestAllPermission() {
        // Camera is a dangerous permission, we need to re-ask permission here
        // if newer or equal to marshamellow version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (allPermissionsEnabled()) {
                // all permissions granted, no need to do anything
                allPermissionsGrantedFlag = 1
            } else {
                setupMultiplePermissions()
            }
            // if older android versions dunnid do this
        } else {
            // it must be older than Marshmallow. As long as AndroidManifest.xml
            // specifies the permissions, nothing else needs to be done
            allPermissionsGrantedFlag = 1
        }
    }

    // For this method only required a certain minsdk then dunnid specify in manifest
    @RequiresApi(Build.VERSION_CODES.M)
    private fun allPermissionsEnabled(): Boolean {
        return permissionList.none {
            // it means items in checkSelfPermission
            checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupMultiplePermissions() {
        val remainingPermissions = permissionList.filter {
            checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED
        }
        requestPermissions(remainingPermissions.toTypedArray(), 101)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissionList: Array<out
    String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissionList, grantResults)
        if (requestCode == 101) {
            if (grantResults.any { it != PackageManager.PERMISSION_GRANTED }) {
                @TargetApi(Build.VERSION_CODES.M)
                if (permissionList.any { shouldShowRequestPermissionRationale(it) }) {
                    AlertDialog.Builder(this)
                            .setMessage("Press Permissions to Decide Permission Again")
                            .setPositiveButton("Permissions") { dialog, which -> setupMultiplePermissions() }
                            .setNegativeButton("Cancel") { dialog, which -> dialog.dismiss() }
                            .create()
                            .show()
                }
            }
        }
    }
}