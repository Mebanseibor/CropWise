package com.example.cropwise

import android.os.Bundle
import android.view.Gravity
import android.view.SurfaceControl.Transaction
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {
    // views
    private lateinit var mainToolBar_title : TextView
    private lateinit var fragCon_main : View


    // debug views
    private lateinit var btnDebugAdvice : Button
    private lateinit var btnDebugFP : Button
    private lateinit var btnDebugTracking : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDebug()
        initViews()
        initToolBar()
        initCategory()
        initDisplayMainCategory()
    }

    private fun initDebug(){
        btnDebugAdvice = findViewById(R.id.btnAdvice)
        btnDebugFP = findViewById(R.id.btnPrecisionFarming)
        btnDebugTracking = findViewById(R.id.btnTracking)

        btnDebugTracking.setOnClickListener {
            loadFragment(Fragment_Tracking_Time())
        }
    }

    private fun initViews(){
        mainToolBar_title = findViewById(R.id.mainToolBar_title)
        fragCon_main = findViewById(R.id.fragCon_main)
    }

    private fun initToolBar(){
        mainToolBar_title.setOnClickListener {
            fun displayToast(){
                var toastView = layoutInflater.inflate(R.layout.toast_custom, findViewById(R.id.fragCon_main), false)

                var toast = Toast(this)
                toast.duration = Toast.LENGTH_SHORT
                toast.view = toastView
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 100)
                toast.show()
            }

            displayToast()
            loadFragment(Fragment_MainMenu_Category())
        }
    }

    private fun initCategory(){
    }

    private fun loadFragment(fragment: androidx.fragment.app.Fragment){
        val transactionMain = supportFragmentManager.beginTransaction()
        transactionMain.replace(R.id.fragCon_main, fragment)
        transactionMain.commit()
    }

    private fun initDisplayMainCategory(){
        loadFragment(Fragment_MainMenu_Category())
    }
}