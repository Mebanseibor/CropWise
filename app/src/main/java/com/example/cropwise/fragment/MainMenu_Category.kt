package com.example.cropwise.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.cropwise.R
import com.example.cropwise.advice.Advice
import com.example.cropwise.toast.displayComingSoon
import com.example.cropwise.tracking.Track

class MainMenu_Category : Fragment(){
    // view
    private var frag : View? = null

    // buttons
    private lateinit var btnAdviceA : Button
    private lateinit var btnAdviceB : Button
    private lateinit var btnAdviceC : Button

    private lateinit var btnTrackingA : Button
    private lateinit var btnTrackingB : Button
    private lateinit var btnTrackingC : Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        frag = inflater.inflate(R.layout.fragment_mainmenu_categories, container, false)

        initViews()

        return frag
    }

    private fun initViews(){
        btnAdviceA = frag!!.findViewById(R.id.btnAdviceA)
        btnAdviceB = frag!!.findViewById(R.id.btnAdviceB)
        btnAdviceC = frag!!.findViewById(R.id.btnAdviceC)

        btnTrackingA = frag!!.findViewById(R.id.btnTrackingA)
        btnTrackingB = frag!!.findViewById(R.id.btnTrackingB)
        btnTrackingC = frag!!.findViewById(R.id.btnTrackingC)

        btnAdviceA.setOnClickListener{
            val intent = Intent(activity, Advice::class.java)
            startActivity(intent)
        }

        btnAdviceB.setOnClickListener{
            val intent = Intent(activity, Advice::class.java)
            intent.putExtra("inputCategory", "Soil")
            startActivity(intent)
        }

        btnTrackingA.setOnClickListener{
            val intent = Intent(activity, Track::class.java)
            startActivity(intent)
        }

        btnAdviceC.setOnClickListener{displayComingSoon(requireContext(), "Supplements")}

        btnTrackingB.setOnClickListener{displayComingSoon(requireContext(), "Sun Angle")}
        btnTrackingC.setOnClickListener{displayComingSoon(requireContext(), "Growing Seasons")}
    }
}