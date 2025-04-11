package com.example.cropwise.tracking

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.cropwise.R
import com.example.cropwise.fragment.Tracking_Time

class Track : AppCompatActivity() {
    // view
    private lateinit var frameLayout : FrameLayout

    // fragment
    private lateinit var fragMng : FragmentManager

    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_track)

        initViews()

        fragMng = supportFragmentManager
        fragMng.beginTransaction()
            .replace(frameLayout.id, Tracking_Time())
            .commit()
    }

    private fun initViews(){
        frameLayout = findViewById(R.id.frameLayout)
    }
}