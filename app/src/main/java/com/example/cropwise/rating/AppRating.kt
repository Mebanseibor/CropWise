package com.example.cropwise.rating

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cropwise.GO
import com.example.cropwise.R
import com.example.cropwise.actionbar.mainToolBar
import com.example.cropwise.mainNotification
import com.example.cropwise.mainToolBar
import com.example.cropwise.toast.displayMsg

class AppRating : AppCompatActivity(){
    // views
    private lateinit var ratingBar : RatingBar
    private lateinit var btnSubmitRating : Button
    private lateinit var btnClearRating: Button
    private lateinit var btnCancelRating : Button
    private lateinit var headerTitle : TextView
    private lateinit var textViewEmotion : TextView
    private lateinit var textViewRatingValue : TextView

    // savedPreference
    private var SP_name: String = "appRating"
    private lateinit var sharedPref : SharedPreferences
    private var keyAppRating : String = "appRating"
    private var SP_appRating : Float = 0.0.toFloat()

    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apprating)

        initViews()
        initFetchSharedPreferences()

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


        val value_appRating = sharedPref.getString(keyAppRating, null)

        if(value_appRating == null){
            Log.d("App Rating", "value_appRating was null")
            SP_appRating = 0.0.toFloat()

            textViewEmotion.setText("")
            textViewRatingValue.setText("")

            return
        }

        Log.d("App Rating", "value_appRating was NOT null")
        SP_appRating = value_appRating.toFloat()

        updateReactionBasedOnRating(SP_appRating)

        Log.d("App Rating", "SP_appRating = ${value_appRating.toFloat()}")
    }

    private fun initViews(){
        ratingBar = findViewById(R.id.ratingBar)
        btnSubmitRating = findViewById(R.id.btnSubmitRating)
        btnCancelRating = findViewById(R.id.btnCancelRating)
        btnClearRating = findViewById(R.id.btnClearRating)
        textViewEmotion = findViewById(R.id.textViewEmotion)
        textViewRatingValue = findViewById(R.id.textViewRatingValue)

        headerTitle = findViewById(R.id.headerTitle)
    }

    private fun updateReactionBasedOnRating(rating : Float){
        var emotion = "default emotion"
        if(rating > 4.0){ emotion = "😊" }
        else if (rating > 3.0){ emotion = "😀" }
        else if (rating > 2.0){ emotion = "🫤" }
        else if (rating > 1.0){ emotion = "☹️" }
        else if (rating >= 0.0){ emotion = "😟" }

        textViewEmotion.setText(emotion)
        textViewRatingValue.setText(rating.toString() + "/${ratingBar.max*ratingBar.stepSize}")
        Log.d("AppRating", "ratingBar.max:\n${ratingBar.max}")
        Log.d("AppRating", "ratingBar.stepSize\n${ratingBar.stepSize}")
    }

    private fun initRatingBar(){
        ratingBar.rating = SP_appRating

        ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            Log.d("AppRating", "New rating changed to ${rating}")
            updateReactionBasedOnRating(rating)
        }

        btnSubmitRating.setOnClickListener{
            val editor = sharedPref.edit()

            val rating = ratingBar.rating

            editor.putString(keyAppRating, rating.toString())
            editor.apply()

            var toastTitle = "Thank You"
            var toastBody = "Thank you for rating the app"
            var colorId : Int? = resources.getColor(R.color.themeMainPrimaryLight)

            if(rating >= 3.5){
                mainNotification.sendNormal("Rating Appreciation", "Thank you for rating us a ${rating}!!\nWe appreciate that you like our application")
            }
            else if(rating >= 2.0){
                mainNotification.sendNormal("Rating Appreciation", "Thank you for rating us a ${rating}!!\nWe hope to serve you better")
                toastBody = toastBody + "\nWe hope to serve you better"
            }
            else {
                mainNotification.sendNormal("Rating Appreciation", "Thank you for rating us a ${rating}.\nSorry for the bad experience\nWe hope to deliver a better experience to you")
                toastBody = toastBody + "\nSorry for the bad experience\nWe hope to deliver a better experience to you"
                colorId = resources.getColor(R.color.cancel)
            }

            displayMsg(this, toastTitle, toastBody, colorId)

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