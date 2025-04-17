package com.example.cropwise.rating

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.cropwise.GO
import com.example.cropwise.R
import com.example.cropwise.actionbar.mainToolBar
import com.example.cropwise.mainNotification
import com.example.cropwise.mainToolBar

class AppRating : AppCompatActivity(){
    // views
    private lateinit var ratingBar : RatingBar
    private lateinit var btnSubmitRating : Button
    private lateinit var btnClearRating: Button
    private lateinit var btnCancelRating : Button
    private lateinit var headerTitle : TextView

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
        initToolBar()

        headerTitle.text = "App Rating"
    }

    private fun initToolBar(){
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.main_toolBar_appRating)
        setSupportActionBar(toolbar)
        mainToolBar(this, toolbar)
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
        btnCancelRating = findViewById(R.id.btnCancelRating)
        btnClearRating = findViewById(R.id.btnClearRating)

        headerTitle = findViewById(R.id.headerTitle)
    }

    private fun initRatingBar(){
        ratingBar.rating = SP_appRating

        ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            Toast.makeText(this, "New rating changed to ${ratingBar.rating}", Toast.LENGTH_SHORT).show()
        }

        btnSubmitRating.setOnClickListener{
            val editor = sharedPref.edit()

            val rating = ratingBar.rating

            editor.putString(keyAppRating, rating.toString())
            editor.apply()

            Toast.makeText(this, "Thanks for rating us ${rating}", Toast.LENGTH_SHORT).show()

            if(rating >= 3.5){ mainNotification.sendNormal("Rating Appreciation", "Thank you for rating us a ${rating}!!\nWe appreciate that you like our application") }
            else if(rating >= 2.0){ mainNotification.sendNormal("Rating Appreciation", "Thank you for rating us a ${rating}!!\nWe hope to serve you better") }
            else { mainNotification.sendNormal("Rating Appreciation", "Thank you for rating us a ${rating}.\nSorry for the bad experience\nWe hope to deliver a better experience to you") }

            finish()
        }

        btnClearRating.setOnClickListener{ratingBar.rating = 0.0.toFloat()}

        btnCancelRating.setOnClickListener{finish()}
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(GO.menuID, menu)

        val menuItemRating = menu?.findItem(R.id.menu_item_rating)
        Log.d("App Rating", "Hiding rating button")
        menuItemRating?.setVisible(false)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.main_toolBar_appRating)

        toolbar.subtitle = ""
        toolbar.title = ""


        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return mainToolBar.setOnOptionsItemSelected(item)
    }
}