package com.example.cropwise

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SettingsActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private lateinit var username: TextView
    private lateinit var email: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)

        val logout = findViewById<Button>(R.id.logout)

        // Initialize username and email properly
        username = findViewById(R.id.username)
        email = findViewById(R.id.email)

        fetchUser()

        logout.setOnClickListener {
            logout()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun logout() {
        auth.signOut()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Clears back stack
        startActivity(intent)
        finish()
    }

    private fun fetchUser() {
        val user = auth.currentUser

        if (user == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        val userId = user.uid

        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    username.text = document.getString("username") ?: "No Username"
                    email.text = document.getString("email") ?: "No Email"
                } else {
                    Toast.makeText(this, "User data not found", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error fetching user data", Toast.LENGTH_SHORT).show()
            }
    }
}
