package com.example.cropwise.fragment

import android.content.Context
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.cropwise.R

class mainFragmentContainer(context: Context, fragmentContainer : View, fragmentManager : FragmentManager){
    public lateinit var transactionMain : FragmentTransaction
    public lateinit var fragmentManager : FragmentManager
    public lateinit var fragmentContainer : View
    public lateinit var context : Context


    init {
        this.context = context
        this.fragmentManager = fragmentManager
        this.fragmentContainer = fragmentContainer
    }

    fun loadFragment(fragment: androidx.fragment.app.Fragment){
        this.transactionMain = fragmentManager.beginTransaction()
        this.transactionMain.replace(R.id.fragCon_main, fragment)
        this.transactionMain.commit()
    }

    fun loadCategory(){
        loadFragment(MainMenu_Category())
    }

    fun loadAdvice(){
        loadFragment(Advice_Crops(this.context))
    }
}