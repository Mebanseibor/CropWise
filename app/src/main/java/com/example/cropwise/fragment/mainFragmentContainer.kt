package com.example.cropwise.fragment

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.cropwise.R

class mainFragmentContainer(activity: Activity, fragmentContainer : View, fragmentManager : FragmentManager){
    public lateinit var transactionMain : FragmentTransaction
    public lateinit var fragmentManager : FragmentManager
    public lateinit var fragmentContainer : View
    public lateinit var activity : Activity


    init {
        this.activity = activity
        this.fragmentManager = fragmentManager
        this.fragmentContainer = fragmentContainer
    }

    fun loadFragment(fragment: androidx.fragment.app.Fragment, isBase: Boolean = false) {
        // clears the backstack
        if (isBase) {
            while (fragmentManager.backStackEntryCount > 0) {
                fragmentManager.popBackStackImmediate()
                Log.d("loadFragment", "Clearing backStack")
            }
        }

        this.transactionMain = fragmentManager.beginTransaction()
        this.transactionMain.replace(R.id.fragCon_main, fragment)

        if (!isBase) {
            this.transactionMain.addToBackStack(null)
            Log.d("loadFragment", "Adding to backStack")
        }

        this.transactionMain.commit()
    }


    fun loadCategory(){
        loadFragment(MainMenu_Category(), true)
    }

    fun loadAdvice(){
        loadFragment(Advice_Crops(activity.baseContext))
    }
}