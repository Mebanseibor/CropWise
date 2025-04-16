package com.example.cropwise.tracking

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.cropwise.GO
import com.example.cropwise.R
import com.example.cropwise.actionbar.mainToolBar
import com.example.cropwise.fragment.Advice_Crops
import com.example.cropwise.fragment.Advice_Soil
import com.example.cropwise.fragment.Track_SunAngle
import com.example.cropwise.fragment.Track_Time
import com.example.cropwise.mainToolBar

class Track : AppCompatActivity() {
    // intent extras
    private var inputCategory : String = ""

    // view
    private lateinit var frameLayout : FrameLayout
    private lateinit var toolBar_track : Toolbar

    // fragment
    private lateinit var fragMng : FragmentManager

    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_track)

        initGetExtras()
        initViews()

        initToolbar()
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

        toolBar_track  = findViewById(R.id.toolBar_track)
    }

    private fun initToolbar(){
        setSupportActionBar(toolBar_track)
        mainToolBar(this, toolBar_track)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(GO.menuID, menu)

        menu?.findItem(R.id.menu_item_rating)?.setVisible(false)

        toolBar_track.title = "Track"
        toolBar_track.subtitle = inputCategory
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return mainToolBar.setOnOptionsItemSelected(item)
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