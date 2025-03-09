package com.example.cropwise.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.cropwise.R

class mainNotification(private val context : Context) {
    lateinit var managerNotification : NotificationManager
    var channelID : String
    var description : String
    var title : String
    lateinit var remoteExpandedViews : RemoteViews
    var sound : MediaPlayer
    var audioAttr : AudioAttributes
    var key : String
    var Id : Int = 0

    init{
        this.channelID = "This is the Channel ID"
        this.description = "This is the Description"
        this.title = "This is the Title"
        this.key = "This is the Key"
        this.sound = MediaPlayer.create(context, R.raw.audio_pop)
        this.audioAttr = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build()
    }

    fun sendNotification(){
        val intentNtf = Intent(context, Normal::class.java)
        val pendingIntentNtf = PendingIntent.getActivity(context, 0, intentNtf, PendingIntent.FLAG_IMMUTABLE)

        createNotificationChannel()
        val notification = buildNotification(pendingIntentNtf)

        this.managerNotification.notify(this.Id, notification)
        Log.d("mainNotification", "Notification was sent")
        this.sound.start()

        this.Id += 1
    }

    private fun buildNotification(pendingIntentNtf : PendingIntent) : Notification{
        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.cropwise_logo)
            .setContentTitle(title)
            .setContentText(description)
            .setSound(Uri.parse("android.resource://" + context.packageName + "/" + R.raw.audio_pop))
            .setContentIntent(pendingIntentNtf)
            .build()

        return notification
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelID,
                title,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = this@mainNotification.description
            }
            managerNotification.createNotificationChannel(channel)
        }
    }
}