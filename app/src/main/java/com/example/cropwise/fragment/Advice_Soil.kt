package com.example.cropwise.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.cropwise.R

class Advice_Soil(private val context : Context) : Fragment() {
    lateinit var adapter : ArrayAdapter<String>
    lateinit var listView : ListView
    lateinit var list : Array<String>

    var frag : View? = null

    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View?{
        frag = layoutInflater.inflate(R.layout.fragment_advice_crops, null, false)

        initViews()
        initList()

        return frag
    }

    private fun initViews(){
        listView = frag!!.findViewById(R.id.listView_advice)
    }

    private fun initList(){
        list = arrayOf("Types of soils", "Tilting soil", "Mixing soil")
        adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, list)
        listView.adapter = adapter
    }
}