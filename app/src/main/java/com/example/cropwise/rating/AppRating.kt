package com.example.cropwise.rating

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cropwise.R

class AppRating : AppCompatActivity(){
    // views
    private lateinit var ratingBar : RatingBar
    private lateinit var btnSubmitRating : Button

    // savedPreference
    private var SP_name: String = "appRating"
    private lateinit var sharedPref : SharedPreferences
    private var keyAppRating : String = "appRating"
    private var SP_appRating : Float = 0.0.toFloat()

    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apprating)

        initFetchSharedPreferences()
        initViews()

        initRatingBar()
    }

    private fun initFetchSharedPreferences(){
        sharedPref = getSharedPreferences(SP_name, MODE_PRIVATE)


        val value_appRating = sharedPref.getString(keyAppRating, "0.0")

        if(value_appRating == null){
            Log.d("App Rating", "value_appRating was null")
            SP_appRating = 0.0.toFloat()
            return
        }

        Log.d("App Rating", "value_appRating was NOT null")
        SP_appRating = value_appRating.toFloat()

        Log.d("App Rating", "SP_appRating = ${value_appRating.toFloat()}")
    }

    private fun initViews(){
        ratingBar = findViewById(R.id.ratingBar)
        btnSubmitRating = findViewById(R.id.btnSubmitRating)
    }

    private fun initRatingBar(){
        ratingBar.rating = SP_appRating

        ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            Toast.makeText(this, "New rating changed to ${ratingBar.rating}", Toast.LENGTH_SHORT).show()
        }

        btnSubmitRating.setOnClickListener{
            val editor = sharedPref.edit()

            editor.putString(keyAppRating, ratingBar.rating.toString())
            editor.apply()

            Toast.makeText(this, "Thanks for rating us ${ratingBar.rating}", Toast.LENGTH_SHORT).show()

            finish()
        }
    }
}