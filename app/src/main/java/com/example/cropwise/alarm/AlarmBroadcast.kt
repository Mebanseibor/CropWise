package com.example.cropwise.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmBroadcast : BroadcastReceiver(){
    // extras
    private var valueReminderText : String? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        valueReminderText = intent?.getStringExtra("reminderText")

        val intentReminder = Intent(context, ReminderClass::class.java).apply{
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra("reminderText", valueReminderText)
        }
        context?.startActivity(intentReminder)
    }
}