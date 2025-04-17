package com.example.cropwise.alarm

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cropwise.R

class ReminderClass : AppCompatActivity() {
    // views
    private lateinit var btnAcknowledge : Button
    private lateinit var textViewReminderText : TextView

    // extra
    private val keyReminderText : String = "reminderText"
    private var valueReminderText : String? = null
    private val defaultValueReminderText : String = "Default reminder text"

    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminderclass)

        Toast.makeText(this, "Reminder Initiated", Toast.LENGTH_SHORT).show()

        initViews()
        initFetchExtras()
    }

    private fun initViews(){
        textViewReminderText = findViewById(R.id.textViewReminderText)
        btnAcknowledge = findViewById(R.id.btnAcknowledge)

        btnAcknowledge.setOnClickListener{
            Toast.makeText(this, "Reminder Acknowledged", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun initFetchExtras(){
        val intent = intent

        valueReminderText = intent.getStringExtra(keyReminderText)
        if(valueReminderText == null){valueReminderText = defaultValueReminderText}

        textViewReminderText.text = valueReminderText
    }
}