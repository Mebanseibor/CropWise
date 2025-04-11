package com.example.cropwise.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cropwise.R

class Track_SunAngle : Fragment(){
    // fragment
    var frag : View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        frag = inflater.inflate(R.layout.fragment_tracking_sunangle, container, false)

        initCheckPermissions()

        return frag
    }

    private fun initCheckPermissions(){

    }

}