package com.example.cropwise.advice

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import com.example.cropwise.R
import com.example.cropwise.actionbar.mainToolBar
import com.example.cropwise.fragment.Advice_Crops
import com.example.cropwise.fragment.Advice_Soil
import com.example.cropwise.fragment.MainMenu_Category
import com.example.cropwise.mainToolBar

class Advice : AppCompatActivity() {
    // views
    private lateinit var frameLayout : FrameLayout
    private lateinit var toolBar_advice : Toolbar
    // fragment
    private lateinit var fragMng : FragmentManager

    // intent extras
    private var inputCategory : String = ""

    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_advice)

        initGetExtras()

        initView()

        loadAdvice()

        initToolbar()
    }

    private fun initToolbar(){
        toolBar_advice = findViewById(R.id.toolBar_advice)

        setSupportActionBar(toolBar_advice)
    }

    override fun onCreateOptionsMenu(menu : Menu?) : Boolean{
        menuInflater.inflate(R.menu.main_toolbar, menu)
        menu?.findItem(R.id.menu_item_rating)?.setVisible(false)

        mainToolBar(this, toolBar_advice)

        toolBar_advice.title = ""
        toolBar_advice.subtitle = ""

        return true
    }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean {
        return mainToolBar.setOnOptionsItemSelected(item)
    }

    private fun initGetExtras(){
        var intent = getIntent()

        var temp = intent.getStringExtra("inputCategory")
        if(temp.isNullOrBlank()){inputCategory = ""}
        else {inputCategory = temp}
    }


    private fun initView(){
        frameLayout = findViewById(R.id.frameLayout)
    }

    private fun loadAdvice(){
        val frag = getFragment(inputCategory)

        fragMng = supportFragmentManager
        fragMng.beginTransaction()
            .replace(frameLayout.id, frag)
            .commit()
    }

    private fun getFragment(category : String) : androidx.fragment.app.Fragment{
        if(category == "Soil"){return Advice_Soil(this)}
        else{return Advice_Crops(this)}
    }
}