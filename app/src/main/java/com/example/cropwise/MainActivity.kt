package com.example.cropwise

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

        GO.mainActionBar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.main_toolBar)
        setSupportActionBar(GO.mainActionBar)
        mainToolBar = mainToolBar(this)

        mainNotification = mainNotification(this)
        mainNotification.managerNotification = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(GO.menuID, menu)
        // GO.actionBarMenu.findItem(R.id.menu_item_home)?.isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return mainToolBar.setOnOptionsItemSelected(item)
    }
}