package com.example.cropwise.fragment

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.cropwise.R

class mainFragmentContainer(fragmentManager : FragmentManager){
    public lateinit var transactionMain : FragmentTransaction
    public lateinit var fragmentManager : FragmentManager

    init {
        this.fragmentManager = fragmentManager
    }

    fun loadFragment(fragment: androidx.fragment.app.Fragment){
        this.transactionMain = fragmentManager.beginTransaction()
        this.transactionMain.replace(R.id.fragCon_main, fragment)
        this.transactionMain.commit()
    }

    fun loadCategory(){
        loadFragment(Fragment_MainMenu_Category())
    }
}