package com.example.cropwise

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.cropwise.actionbar.mainToolBar
import com.example.cropwise.notification.mainNotification
import com.example.cropwise.fragment.Tracking_Time
import com.example.cropwise.fragment.mainFragmentContainer


// objects
lateinit var mainFragmentContainer : mainFragmentContainer
lateinit var mainToolBar : mainToolBar
lateinit var mainNotification : mainNotification

class MainActivity : AppCompatActivity() {
    // debug views
    private lateinit var btnDebugAdvice : Button
    private lateinit var btnDebugPF : Button
    private lateinit var btnDebugTracking : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDebug()

        mainFragmentContainer = mainFragmentContainer(this, findViewById(R.id.fragCon_main), this.supportFragmentManager)
        mainFragmentContainer.loadCategory()

        mainToolBar = mainToolBar(this, findViewById(R.id.main_toolBar))
        setSupportActionBar(mainToolBar.mainActionBar)

        mainNotification = mainNotification(this)
        mainNotification.managerNotification = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private fun initDebug(){
        btnDebugAdvice = findViewById(R.id.btnAdvice)
        btnDebugPF = findViewById(R.id.btnPrecisionFarming)
        btnDebugTracking = findViewById(R.id.btnTracking)

        btnDebugTracking.setOnClickListener {
            mainFragmentContainer.loadFragment(Tracking_Time())
        }

        btnDebugAdvice.setOnClickListener {
            mainFragmentContainer.loadAdvice()
        }

        btnDebugPF.setOnClickListener {
            mainNotification.sendNotification()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(mainToolBar.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return mainToolBar.setOnOptionsItemSelected(item)
    }
}