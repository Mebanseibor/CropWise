package com.example.cropwise.actionbar

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.cropwise.R
import com.example.cropwise.SettingsActivity
import com.example.cropwise.fragment.MainMenu_Category
import com.example.cropwise.mainFragmentContainer
import com.example.cropwise.toast.displayAboutApp
import com.example.cropwise.toast.displayComingSoon

class mainToolBar(private val context : Context , mainActionBar : Toolbar){
    lateinit var mainActionBar : Toolbar
    var menu : Int = R.menu.main_toolbar

    init {
        this.mainActionBar = mainActionBar

        mainActionBar.setOnLongClickListener{
            displayAboutApp(context)

            true
        }

        mainActionBar.setOnClickListener{
            mainFragmentContainer.loadFragment(MainMenu_Category())
        }
    }

    fun setOnOptionsItemSelected(item : MenuItem) : Boolean{
        val itemSelected = when(item.itemId){
            R.id.menu_item_home -> {
                mainFragmentContainer.loadCategory()
                true
            }
            R.id.menu_item_settings -> {

                val intent = Intent(context, SettingsActivity::class.java)
                context.startActivity(intent)
//                displayComingSoon(context, "Settings")
                true
            }
            else -> {true}
        }
        return itemSelected
    }
}