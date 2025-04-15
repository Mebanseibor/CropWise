package com.example.cropwise.advice

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.cropwise.R
import com.example.cropwise.fragment.Advice_Crops
import com.example.cropwise.fragment.Advice_Soil

class Advice : AppCompatActivity() {
    // views
    private lateinit var frameLayout : FrameLayout

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