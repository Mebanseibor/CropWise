package com.example.cropwise.actionbar

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.example.cropwise.R
import com.example.cropwise.SettingsActivity
import com.example.cropwise.fragment.MainMenu_Category
import com.example.cropwise.mainFragmentContainer
import com.example.cropwise.notification.NotificationActivity
import com.example.cropwise.toast.displayAboutApp

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
                true
            }
            R.id.menu_item_notification -> {
                val intent = Intent(context, NotificationActivity::class.java)
                context.startActivity(intent)
                true
            }
            else -> {true}
        }
        return itemSelected
    }
}