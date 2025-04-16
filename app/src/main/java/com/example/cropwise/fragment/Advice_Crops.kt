package com.example.cropwise.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cropwise.R
import com.google.firebase.firestore.FirebaseFirestore

class Advice_Crops(private val context: Context) : Fragment() {

    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var listView: ListView
    private lateinit var textViewTitle: TextView
    private val list = ArrayList<String>()
    private val db = FirebaseFirestore.getInstance()
    private var frag: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        frag = inflater.inflate(R.layout.fragment_advice_crops, container, false)
        if (frag == null) return null

        initViews()

        // Use simple_list_item_2 for better formatting (optional)
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_2, android.R.id.text1, list)
        listView.adapter = adapter

        fetchAdviceFromFirestore()
        updateTitle()

        val footerView = layoutInflater.inflate(R.layout.footer_advice, null)
        listView.addFooterView(footerView)

        return frag
    }

    private fun initViews() {
        textViewTitle = frag!!.findViewById(R.id.textViewTitle)
        listView = frag!!.findViewById(R.id.listView_advice)
    }

    private fun fetchAdviceFromFirestore() {
        db.collection("crop_advice")
            .get()
            .addOnSuccessListener { documents ->
                for (doc in documents) {
                    val title = doc.getString("title") ?: continue
                    val category = doc.getString("category") ?: "Unknown"
                    list.add("$title\nCategory: $category")
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateTitle() {
        textViewTitle.text = "Advice on Crops"
    }
}
