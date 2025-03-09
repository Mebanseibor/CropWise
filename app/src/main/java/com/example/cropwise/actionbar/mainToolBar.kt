package com.example.cropwise.actionbar

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.cropwise.R
import com.example.cropwise.fragment.Fragment_MainMenu_Category
import com.example.cropwise.mainFragmentContainer

class mainToolBar(context : Context , mainActionBar : Toolbar){
    lateinit var mainActionBar : Toolbar
    var menu : Int = R.menu.main_toolbar


    init {
        this.mainActionBar = mainActionBar

        mainActionBar.setOnLongClickListener{
            fun displayToast(){
                val toastView = LayoutInflater.from(context).inflate(R.layout.toast_custom, null, false)

                val toast = Toast(context)
                toast.duration = Toast.LENGTH_SHORT
                toast.view = toastView
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 100)
                toast.show()
            }

            displayToast()
            true
        }

        mainActionBar.setOnClickListener{
            mainFragmentContainer.loadFragment(Fragment_MainMenu_Category())
        }
    }

    fun setOnOptionsItemSelected(item : MenuItem) : Boolean{
        val itemSelected = when(item.itemId){
            R.id.menu_item_home -> {
                mainFragmentContainer.loadFragment(Fragment_MainMenu_Category())
                true
            }
            else -> {true}
        }
        return itemSelected
    }
}