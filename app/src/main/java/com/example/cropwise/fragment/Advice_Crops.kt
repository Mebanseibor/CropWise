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

class Advice_Crops(private val context : Context) : Fragment() {
    lateinit var adapter : ArrayAdapter<String>
    lateinit var listView : ListView
    lateinit var list : Array<String>

    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View?{
        var view = layoutInflater.inflate(R.layout.fragment_advice_crops, null, false)
        listView = view.findViewById(R.id.listView_advice)

        initFunctions()

        return view
    }

    private fun initFunctions(){
        list = arrayOf("A", "B", "C", "D", "E")
        adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, list)
        listView.adapter = adapter
    }
}