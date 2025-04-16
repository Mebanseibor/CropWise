package com.example.cropwise.fragment

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import com.example.cropwise.R
import com.example.cropwise.toast.displayPermissionNotGranted
import kotlinx.coroutines.runBlocking

class Track_SunAngle : Fragment(){
    // fragment
    var frag : View? = null

    // permissions
    private val permissionArray = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        frag = inflater.inflate(R.layout.fragment_tracking_sunangle, container, false)

        checkPermissions()

        return frag
    }

    private fun isPermissionGranted(permission : String) : Boolean = ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED

    private fun checkPermissions() : Boolean{
        for (permission in permissionArray) {
            val isPermissionGranted = isPermissionGranted(permission)

            if (isPermissionGranted){
                Log.d("Track SunAngle", "Permission granted\n${permission}")
                continue
            }

            Log.d("Track SunAngle", "Permission not granted\n${permission}")
            displayPermissionNotGranted(requireContext(), permission)

            this.requestPermissions(arrayOf(permission), 101)

            return false
        }

        Log.d("Track SunAngle", "ALL Permission granted")
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.d("Track SunAngle", "Start of override onRequestPermissionsResult()")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d("Track SunAngle", "End of super")

        for(permission in permissions){
            if (!isPermissionGranted(permission)){activity?.finish()}
        }
    }
}