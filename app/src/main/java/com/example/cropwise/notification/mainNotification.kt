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
import com.example.cropwise.MainActivity
import com.example.cropwise.R

class mainNotification(private val context : Context) {
    lateinit var managerNotification : NotificationManager
    var channelID : String
    var title : String
    var description : String
    lateinit var remoteExpandedViews : RemoteViews
    var sound : MediaPlayer
    var audioAttr : AudioAttributes
    var key : String
    var Id : Int = 0

    init{
        this.channelID = "This is the Channel ID"
        this.title = "This is the Title"
        this.description = "This is the Description"
        this.key = "This is the Key"
        this.sound = MediaPlayer.create(context, R.raw.audio_pop)
        this.audioAttr = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build()
    }

    fun sendNormal(title : String, description : String, intent : Intent? = null){

        this.title = title
        this.description = description

        fun buildNotification(pendingIntent : PendingIntent? = null) : Notification{
            var tempNtf = NotificationCompat.Builder(context, this.channelID)
                .setSmallIcon(R.drawable.cropwise_logo)
                .setContentTitle(title)
                .setContentText(description)
                .setSound(Uri.parse("android.resource://" + context.packageName + "/" + R.raw.audio_pop))
                .setAutoCancel(true)

            if (pendingIntent != null) {tempNtf.setContentIntent(pendingIntent)}

            var notification = tempNtf.build()

            return notification
        }

        var pendingIntent : PendingIntent? = null

        if (intent != null) {
            pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        }

        val notification = buildNotification(pendingIntent)

        createNotificationChannel()
        this.managerNotification.notify(this.Id, notification)
        Log.d("mainNotification", "Notification was sent")

        this.Id += 1
    }

    fun sendReminder(title : String, description : String){
        fun reminderNotification(pendingIntent : PendingIntent) : Notification{
            val notification = NotificationCompat.Builder(context, this.channelID)
                .setContentTitle(this.title)
                .setContentText(this.description)
                .setSmallIcon(R.drawable.baseline_alarm_24)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            return notification
        }

        this.channelID = "Reminder"
        this.title = title
        this.description = description
        this.sound = MediaPlayer.create(context, R.raw.audio_notification)

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        createNotificationChannel()
        val notification = reminderNotification(pendingIntent)

        this.managerNotification.notify(this.Id, notification)
        Log.d("reminderNotification", "Reminder Notification was sent")

        this.Id += 1
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(this.channelID, this.title, NotificationManager.IMPORTANCE_DEFAULT)
                .apply {description = this@mainNotification.description}

            managerNotification.createNotificationChannel(channel)
        }
    }
}