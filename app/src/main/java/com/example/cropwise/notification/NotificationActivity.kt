package com.example.cropwise.notification

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.cropwise.R

class NotificationActivity : AppCompatActivity() {
    lateinit var mainNotification : mainNotification

    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        initDebugger()

        this.mainNotification = mainNotification(this)
        this.mainNotification.managerNotification = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private fun initDebugger(){
        val btnNormal : Button = findViewById(R.id.debugNtfNormal)
        val btnReminder : Button = findViewById(R.id.debugNtfReminder)
        val btnAlert : Button = findViewById(R.id.debugNtfAlert)

        btnNormal.setOnClickListener{
            mainNotification.sendNormal("Normal", "Manually triggered")
        }

        btnReminder.setOnClickListener{
            mainNotification.sendReminder("Reminder", "Manually triggered")
        }

        btnAlert.setOnClickListener{
            mainNotification.sendAlert("Alert", "Manually triggered")
        }
    }
}