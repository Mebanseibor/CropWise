package com.example.cropwise

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.cropwise.actionbar.mainToolBar
import com.example.cropwise.fragment.Advice_Crops
import com.example.cropwise.fragment.Tracking_Time
import com.example.cropwise.fragment.mainFragmentContainer


// objects
lateinit var mainFragmentContainer : mainFragmentContainer
lateinit var mainToolBar : mainToolBar

class MainActivity : AppCompatActivity() {
    // debug views
    private lateinit var btnDebugAdvice : Button
    private lateinit var btnDebugFP : Button
    private lateinit var btnDebugTracking : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDebug()

        mainFragmentContainer = mainFragmentContainer(this, findViewById(R.id.fragCon_main), this.supportFragmentManager)
        mainFragmentContainer.loadCategory()

        mainToolBar = mainToolBar(this, findViewById(R.id.main_toolBar))
        setSupportActionBar(mainToolBar.mainActionBar)
    }

    private fun initDebug(){
        btnDebugAdvice = findViewById(R.id.btnAdvice)
        btnDebugFP = findViewById(R.id.btnPrecisionFarming)
        btnDebugTracking = findViewById(R.id.btnTracking)

        btnDebugTracking.setOnClickListener {
            mainFragmentContainer.loadFragment(Tracking_Time())
        }

        btnDebugAdvice.setOnClickListener {
            mainFragmentContainer.loadAdvice()
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