package com.example.cropwise.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.cropwise.R

class DailyMessage : Fragment() {
    // fragment
    private var frag : View? = null

    // views
    private lateinit var dailyMsgTitle : TextView
    private lateinit var dailyMsgText: TextView

    // messages
    private var listMessage : List<String> = listOf(
        "Test your soil regularly and add organic matter like compost or cover crops to improve fertility and structure.",
        "Rotate crops each season to prevent soil exhaustion and reduce the buildup of pests and diseases.",
        "Use precision farming tools such as GPS-guided tractors or drones to optimize planting, irrigation, and fertilization.",
        "Install drip irrigation systems and practice efficient water management to conserve water and deliver it directly to plant roots.",
        "Select high-quality seeds with good germination rates and disease resistance for better crop yields.",
        "Prepare your soil by ploughing, levelling, and manuring before planting to ensure optimal conditions for growth.",
        "Choose crops suited to your soil type, climate, and market demand for maximum profitability and sustainability.",
        "Implement integrated pest management (IPM) to control pests using biological, cultural, and chemical methods as needed.",
        "Maintain farm equipment regularly to prevent breakdowns and ensure efficient operations throughout the season.",
        "Keep detailed records of planting dates, crop varieties, weather, and yields to make informed decisions for future seasons."
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View?{
        frag = layoutInflater.inflate(R.layout.fragment_dailymessage, null)

        if(frag == null)return frag

        initView()

        initDisplayMessage()

        return frag
    }

    private fun initView(){
        dailyMsgTitle = frag!!.findViewById<TextView>(R.id.dailyMsgTitle)
        dailyMsgText = frag!!.findViewById<TextView>(R.id.dailyMsgText)
    }

    private fun fetchRandomMessage() : String = listMessage.random()

    private fun initDisplayMessage(){
        dailyMsgText.text = fetchRandomMessage()
    }
}