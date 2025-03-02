package com.example.cropwise

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity(){
    private lateinit var cropwiseEmblem : ImageView

    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splashscreen)

        initFindViews()
        initAnimation()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
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
}