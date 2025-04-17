package com.example.cropwise.fragment

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cropwise.R
import com.example.cropwise.alarm.AlarmBroadcast
import com.example.cropwise.alarm.ReminderClass

class Track_Time : Fragment(){
    // fragment
    private var frag : View? = null

    // views
    private lateinit var btnSetReminder : Button
    private lateinit var btnClearText : Button
    private lateinit var editTextReminderText : EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        frag = inflater.inflate(R.layout.fragment_tracking_time, container, false)

        if(frag == null) {return frag}

        initViews()

        return frag
    }

    private fun initViews(){
        editTextReminderText = frag!!.findViewById(R.id.editTextReminderText)
        btnClearText = frag!!.findViewById(R.id.btnClearText)
        btnSetReminder = frag!!.findViewById(R.id.btnSetReminder)

        btnClearText.setOnClickListener{
            editTextReminderText.setText("")
        }

        btnSetReminder.setOnClickListener{
            setReminder()
            Toast.makeText(requireContext(), "Reminder has been set", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setReminder(){
        val delayMillis = System.currentTimeMillis() + 1000L

        val inputtedText = editTextReminderText.text.toString()

        val intent = Intent(context, AlarmBroadcast::class.java).apply{
            putExtra("reminderText", inputtedText)
        }

        val flags = PendingIntent.FLAG_UPDATE_CURRENT or
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_MUTABLE
                else 0

        val pendingIntent = PendingIntent.getBroadcast(requireContext(), 101, intent, flags)

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            delayMillis,
            pendingIntent
        )
    }
}