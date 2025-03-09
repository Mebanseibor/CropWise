package com.example.cropwise

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.SurfaceControl.Transaction
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {
    // views
    private lateinit var mainActionBar : androidx.appcompat.widget.Toolbar
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
        fun initActionBar(){
            mainActionBar = findViewById(R.id.main_toolBar)
            setSupportActionBar(mainActionBar)
        }

        initActionBar()
        fragCon_main = findViewById(R.id.fragCon_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemSelected = when(item.itemId){
            R.id.menu_item_home -> {
                loadFragment(Fragment_MainMenu_Category())
                true
            }
            else -> {super.onOptionsItemSelected(item)}
        }
        return itemSelected
    }

    private fun initToolBar(){
        mainActionBar.setOnLongClickListener{
            fun displayToast(){
                val toastView = layoutInflater.inflate(R.layout.toast_custom, findViewById(R.id.fragCon_main), false)

                val toast = Toast(this)
                toast.duration = Toast.LENGTH_SHORT
                toast.view = toastView
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 100)
                toast.show()
            }

            displayToast()
            true
        }

        mainActionBar.setOnClickListener{
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