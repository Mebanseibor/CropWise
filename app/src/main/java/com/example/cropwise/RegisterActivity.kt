package com.example.cropwise

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegisterActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        val textView = findViewById<TextView>(R.id.textView)

        val button = findViewById<Button>(R.id.button)

        val username = findViewById<EditText>(R.id.editTextUsername)
        val email = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val password = findViewById<EditText>(R.id.editTextTextPassword)


        if (auth.app != null) {
            Log.d("FirebaseCheck", "🔥 Firebase Auth is connected!")
        } else {
            Log.e("FirebaseCheck", "❌ Firebase Auth is NOT connected!")
        }


        button.setOnClickListener{
            val email = email.text.toString()
            val password = password.text.toString()
            val username = username.text.toString()

            if(email.isBlank() || password.isBlank() || username.isBlank()) {

                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener;
            }

            registerUser(email, password, username)
        }


        textView.setOnClickListener {

            var intent = Intent(this, LoginActivity::class.java);

            startActivity(intent);

        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun registerUser(email: String, password: String, username: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    Log.d("Auth", "✅ User registration successful: $userId")

                    if (userId != null) {
                        val user = hashMapOf(
                            "username" to username,
                            "email" to email
                        )

                        Toast.makeText(this, "Registration successful! Please log in.", Toast.LENGTH_SHORT).show()
                        db.collection("users").document(userId)
                            .set(user)
                            .addOnSuccessListener {
                                Log.d("Firestore", "✅ User data saved for ID: $userId")
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(baseContext, "Error saving user data", Toast.LENGTH_SHORT).show()
                                Log.e("Firestore", "❌ Firestore error: ", e)
                            }
                    }
                } else {
                    Log.e("Auth", "❌ Registration failed: ${task.exception?.message}")
                    Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }


}