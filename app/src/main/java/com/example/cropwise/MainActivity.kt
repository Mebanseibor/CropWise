package com.example.cropwise

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.cropwise.actionbar.initToolBar
import com.example.cropwise.fragment.Fragment_MainMenu_Category
import com.example.cropwise.fragment.Fragment_Tracking_Time
import com.example.cropwise.fragment.mainFragment


// fragments
lateinit var mainFragment : mainFragment

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
        mainFragment = mainFragment(this.supportFragmentManager)
        initToolBar(this, mainActionBar)
        initCategory()
        initDisplayMainCategory()
    }

    private fun initDebug(){
        btnDebugAdvice = findViewById(R.id.btnAdvice)
        btnDebugFP = findViewById(R.id.btnPrecisionFarming)
        btnDebugTracking = findViewById(R.id.btnTracking)

        btnDebugTracking.setOnClickListener {
            mainFragment.loadFragment(Fragment_Tracking_Time())
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
                mainFragment.loadFragment(Fragment_MainMenu_Category())
                true
            }
            else -> {super.onOptionsItemSelected(item)}
        }
        return itemSelected
    }

    private fun initCategory(){
    }

    private fun initDisplayMainCategory(){
        mainFragment.loadFragment(Fragment_MainMenu_Category())
    }
}