package com.example.cropwise.rating

import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cropwise.R

class AppRating : AppCompatActivity(){
    // views
    private lateinit var ratingBar : RatingBar
    private lateinit var btnSubmitRating : Button

    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apprating)

        initViews()

        initRatingBar()
    }

    private fun initViews(){
        ratingBar = findViewById(R.id.ratingBar)
        btnSubmitRating = findViewById(R.id.btnSubmitRating)
    }

    private fun initRatingBar(){
        ratingBar.rating = 0.0.toFloat()

        ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            Toast.makeText(this, "New rating changed to ${ratingBar.rating}", Toast.LENGTH_SHORT).show()
        }

        btnSubmitRating.setOnClickListener{
            Toast.makeText(this, "Thanks for rating us ${ratingBar.rating}", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}