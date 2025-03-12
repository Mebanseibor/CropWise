package com.example.cropwise.notification

import android.app.Activity
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

class mainNotification(activity : Activity) {
    lateinit var managerNotification : NotificationManager
    var channelID : String
    var title : String
    var description : String
    var activity : Activity
    lateinit var remoteExpandedViews : RemoteViews
    var sound : MediaPlayer
    var audioAttr : AudioAttributes
    var key : String
    var Id : Int = 0

    init{
        this.activity = activity
        this.channelID = "This is the Channel ID"
        this.title = "This is the Title"
        this.description = "This is the Description"
        this.key = "This is the Key"
        this.sound = MediaPlayer.create(activity.baseContext, R.raw.audio_pop)
        this.audioAttr = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build()
    }

    fun sendNormal(title : String, description : String, intent : Intent? = null){
        fun buildNotification(pendingIntent : PendingIntent? = null) : Notification{
            val notification = NotificationCompat.Builder(activity.baseContext, this.channelID)
                .setSmallIcon(R.drawable.baseline_notifications_24)
                .setContentTitle(this.title)
                .setContentText(this.description)
                .setSound(Uri.parse("android.resource://" + activity.baseContext.packageName + "/" + R.raw.audio_pop))
                .setAutoCancel(true)
                .apply{
                    if (pendingIntent != null) {setContentIntent(pendingIntent)}
                }
                .build()

            return notification
        }

        this.title = title
        this.description = description

        var pendingIntent : PendingIntent? = null
        if (intent != null) {
            pendingIntent = PendingIntent.getActivity(activity.baseContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        }

        val notification = buildNotification(pendingIntent)

        val channel = NotificationChannel(this.channelID, "Normal", NotificationManager.IMPORTANCE_DEFAULT)
            .apply{
                this.description = this@mainNotification.description
            }

        createNotificationChannel(channel)

        this.managerNotification.notify(this.Id, notification)
        Log.d("mainNotification", "Notification was sent")

        this.Id++
    }

    fun sendReminder(title : String, description : String){
        fun reminderNotification(pendingIntent : PendingIntent) : Notification{
            val notification = NotificationCompat.Builder(activity.baseContext, this.channelID)
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
        this.sound = MediaPlayer.create(activity.baseContext, R.raw.audio_notification)

        val intent = Intent(activity.baseContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(activity.baseContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val channel = NotificationChannel(this.channelID, "Reminders", NotificationManager.IMPORTANCE_DEFAULT)
            .apply{
                this.description = this@mainNotification.description
            }

        createNotificationChannel(channel)
        val notification = reminderNotification(pendingIntent)

        this.managerNotification.notify(this.Id, notification)
        Log.d("reminderNotification", "Reminder Notification was sent")

        this.Id++
    }

    fun sendAlert(title : String, description : String, intent : Intent? = null){
        fun alertNotification(pendingIntent : PendingIntent? = null) : Notification{
            val notification = NotificationCompat.Builder(activity.baseContext, this.channelID)
                .setContentTitle(this.title)
                .setContentText(this.description)
                .setSmallIcon(R.drawable.baseline_notification_important_24)
                .setAutoCancel(true)
                .apply{
                    if(intent != null){setContentIntent(pendingIntent)}
                }
                .build()

            return notification
        }

        this.channelID = "Alert"
        this.title = title
        this.description = description
        this.sound = MediaPlayer.create(activity.baseContext, R.raw.audio_alert)


        var pendingIntent : PendingIntent? = null
        if (intent != null) {
            pendingIntent = PendingIntent.getActivity(activity.baseContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        }


        val channel = NotificationChannel(this.channelID, "Alert", NotificationManager.IMPORTANCE_HIGH)
            .apply{
                this.description = this@mainNotification.description
            }

        createNotificationChannel(channel)
        this.managerNotification.notify(this.Id, alertNotification(pendingIntent))

        this.Id++;
    }


    private fun createNotificationChannel(channel : NotificationChannel){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            managerNotification.createNotificationChannel(channel)
        }
    }
}