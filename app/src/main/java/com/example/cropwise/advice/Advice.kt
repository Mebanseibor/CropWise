package com.example.cropwise.advice

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.cropwise.R
import com.example.cropwise.fragment.Advice_Crops

class Advice : AppCompatActivity() {
    // views
    private lateinit var frameLayout : FrameLayout

    // fragment
    private lateinit var fragMng : FragmentManager

    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_advice)

        initView()

        loadAdvice()
    }

    private fun initView(){
        frameLayout = findViewById(R.id.frameLayout)
    }

    private fun loadAdvice(inputCategory : String = "default"){
        var category = inputCategory

        if (category == "default"){category = "Crops"}

        fragMng = supportFragmentManager
        fragMng.beginTransaction()
            .replace(frameLayout.id, Advice_Crops(this))
            .commit()
    }
}