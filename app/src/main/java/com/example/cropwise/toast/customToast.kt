package com.example.cropwise.toast

import android.content.Context
import android.graphics.Color
import android.media.MediaPlayer
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.cropwise.R

fun displayAboutApp(context : Context) {
    val toastView = LayoutInflater.from(context).inflate(R.layout.toast_custom, null, false)

    val toast = Toast(context)
    toast.duration = Toast.LENGTH_SHORT
    toast.view = toastView
    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 100)
    toast.show()

    val audio = MediaPlayer.create(context, R.raw.audio_pop)
    audio.start()
}

fun displayMsg(context : Context, title : String = "Default Title", body : String = "Default Body", color : Int? = null) {
    val toastView = LayoutInflater.from(context).inflate(R.layout.toast_thankyou, null, false)

    if (color != null){ toastView.findViewById<LinearLayout>(R.id.main).setBackgroundColor(color) }
    toastView.findViewById<TextView>(R.id.textViewTitle).setText(title)
    toastView.findViewById<TextView>(R.id.textViewBody).setText(body)

    val toast = Toast(context)
    toast.duration = Toast.LENGTH_SHORT
    toast.view = toastView
    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 100)
    toast.show()

    val audio = MediaPlayer.create(context, R.raw.audio_pop)
    audio.start()
}

fun displayComingSoon(context : Context, feature : String = "Default feature"){
    Toast.makeText(context, feature + " coming soon" , Toast.LENGTH_SHORT).show()
}

fun displayPermissionNotGranted(context : Context , permission : String = "Default permission"){
    Toast.makeText(context, "Permission not Granted\n${permission}" , Toast.LENGTH_SHORT).show()
    Log.d("toast", "Permission not Granted\t${permission}")
}