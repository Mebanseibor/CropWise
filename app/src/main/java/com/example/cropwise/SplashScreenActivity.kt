package com.example.cropwise

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth

class SplashScreenActivity : AppCompatActivity(){
    private lateinit var cropwiseEmblem : ImageView

    val auth = FirebaseAuth.getInstance();

    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splashscreen)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        initFindViews()
        initAnimation()

        Handler(Looper.getMainLooper()).postDelayed({
            checkUserSession()
        }, 4000)
    }

    private fun initFindViews(){
        cropwiseEmblem = findViewById(R.id.cropwise_emblem)
    }

    private fun initAnimation(){
        fun animateEmblem() {
            val animZoom = AnimationUtils.loadAnimation(this, R.anim.zoom_centertocenter)
            cropwiseEmblem.startAnimation(animZoom)
        }
        animateEmblem()
    }

    private fun checkUserSession() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // User is logged in, go to HomeActivity
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            // No user logged in, go to LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish() // Close SplashScreenActivity
    }
}