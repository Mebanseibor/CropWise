package com.example.cropwise.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.cropwise.R
import com.google.firebase.firestore.FirebaseFirestore

class Advice_Crops(private val context : Context) : Fragment() {

    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val list = ArrayList<String>()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_advice_crops, container, false)

        listView = view.findViewById(R.id.listView_advice)

        // Use a built-in Android layout with slightly more padding & better style
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_2, android.R.id.text1, list)
        listView.adapter = adapter

        fetchAdviceFromFirestore()

        return view
    }

    private fun fetchAdviceFromFirestore() {
        db.collection("crop_advice")
            .get()
            .addOnSuccessListener { documents ->
                for (doc in documents) {
                    val title = doc.getString("title") ?: continue
                    val category = doc.getString("category") ?: "Unknown"
                    // Fancy formatting here
                    list.add("$title\nCategory: $category")
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
