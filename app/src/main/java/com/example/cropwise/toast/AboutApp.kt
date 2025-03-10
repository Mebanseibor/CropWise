package com.example.cropwise.toast

import android.content.Context
import android.media.MediaPlayer
import android.view.Gravity
import android.view.LayoutInflater
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