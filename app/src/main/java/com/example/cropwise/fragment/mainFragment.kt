package com.example.cropwise.fragment

import androidx.fragment.app.FragmentTransaction
import com.example.cropwise.R

lateinit var transactionMain : FragmentTransaction

fun loadFragment(fragment: androidx.fragment.app.Fragment){
    transactionMain.replace(R.id.fragCon_main, fragment)
    transactionMain.commit()
}