package com.example.cropwise.actionbar

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.cropwise.GO
import com.example.cropwise.R
import com.example.cropwise.SettingsActivity
import com.example.cropwise.rating.AppRating
import com.example.cropwise.toast.displayAboutApp
import com.example.cropwise.toast.displayComingSoon

class mainToolBar(private val context : Context){

    init {
        GO.mainActionBar.setNavigationIcon(R.drawable.baseline_arrow_back_24)

        GO.mainActionBar.setNavigationOnClickListener{
            (context as? AppCompatActivity)?.onBackPressed()
        }

        GO.mainActionBar.setOnLongClickListener{
            displayAboutApp(context)
            true
        }
    }

    fun setOnOptionsItemSelected(item: MenuItem): Boolean {
        val itemSelected = when (item.itemId) {
            R.id.menu_item_home -> {
                displayComingSoon(context, "Home")
                true
            }
            R.id.menu_item_settings -> {

                val intent = Intent(context, SettingsActivity::class.java)
                context.startActivity(intent)
                true
            }
            R.id.menu_item_rating-> {
                val intent = Intent(context, AppRating::class.java)
                context.startActivity(intent)
                true
            }
            else -> {true}
        }
        return itemSelected
    }
}