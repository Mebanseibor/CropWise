package com.example.cropwise

import android.app.NotificationManager
import android.content.Context
import android.content.pm.ConfigurationInfo
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cropwise.actionbar.mainToolBar
import com.example.cropwise.notification.mainNotification


// objects
lateinit var mainToolBar : mainToolBar
lateinit var mainNotification : mainNotification

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initOrientation()

        GO.mainActionBar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.main_toolBar)
        setSupportActionBar(GO.mainActionBar)
        mainToolBar = mainToolBar(this, GO.mainActionBar)

        mainNotification = mainNotification(this)
        mainNotification.managerNotification = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        greetTheUser()
        Log.d("Main Activity", "END of override fun onCreate")
    }

    private fun initOrientation(){
        var orientation = resources.configuration.orientation

        if(orientation != Configuration.ORIENTATION_PORTRAIT){
                Toast.makeText(this, "Landscape Mode is on", Toast.LENGTH_SHORT).show()
            }
        }

    private fun greetTheUser(){
        mainNotification.sendNormal("Greetings!", "Welcome Back")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(GO.menuID, menu)

        GO.actionBarMenu = menu

        val menuItemHome = menu?.findItem(R.id.menu_item_home)
        Log.d("App Rating", "Hiding home button")
        menuItemHome?.setVisible(false)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return mainToolBar.setOnOptionsItemSelected(item)
    }
}