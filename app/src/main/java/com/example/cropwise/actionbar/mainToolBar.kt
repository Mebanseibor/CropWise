package com.example.cropwise.actionbar

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.cropwise.MainActivity
import com.example.cropwise.R
import com.example.cropwise.SettingsActivity
import com.example.cropwise.rating.AppRating
import com.example.cropwise.toast.displayAboutApp

class mainToolBar(private val context : Context, toolbar : Toolbar){

    init {
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)

        toolbar.setNavigationOnClickListener{
            (context as? AppCompatActivity)?.onBackPressed()
        }

        toolbar.setOnLongClickListener{
            displayAboutApp(context)
            true
        }

        toolbar.setLogo(R.drawable.cropwise_logo)
    }

    fun setOnOptionsItemSelected(item: MenuItem): Boolean {
        val itemSelected = when (item.itemId) {
            R.id.menu_item_home -> {
                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent)
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