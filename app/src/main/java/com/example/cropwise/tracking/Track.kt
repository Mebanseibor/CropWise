package com.example.cropwise.tracking

import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.cropwise.R
import com.example.cropwise.fragment.Advice_Crops
import com.example.cropwise.fragment.Advice_Soil
import com.example.cropwise.fragment.Track_SunAngle
import com.example.cropwise.fragment.Track_Time

class Track : AppCompatActivity() {
    // intent extras
    private var inputCategory : String = ""

    // view
    private lateinit var frameLayout : FrameLayout

    // fragment
    private lateinit var fragMng : FragmentManager

    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_track)

        initGetExtras()
        initViews()

        loadTracking()
    }

    private fun initGetExtras(){
        var intent = getIntent()

        var temp = intent.getStringExtra("inputCategory")

        if(temp.isNullOrBlank()){inputCategory = ""}
        else {inputCategory = temp}
    }


    private fun initViews(){
        frameLayout = findViewById(R.id.frameLayout)
    }

    private fun loadTracking(){
        var fragment = getFragment(inputCategory)

        fragMng = supportFragmentManager
        fragMng.beginTransaction()
            .replace(frameLayout.id, fragment)
            .commit()
    }

    private fun getFragment(category : String) : androidx.fragment.app.Fragment{
        if(category == "Sun Angle"){return Track_SunAngle()}
        else{return Track_Time()}
    }
}