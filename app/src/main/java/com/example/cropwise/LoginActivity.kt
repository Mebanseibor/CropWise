package com.example.cropwise

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    lateinit var progressBar : ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.button)

        val textView = findViewById<TextView>(R.id.textView)

        progressBar = findViewById<ProgressBar>(R.id.progressBar)

        textView.setOnClickListener {

            var intent = Intent(this, RegisterActivity::class.java);



            startActivity(intent);

        }

        loginButton.setOnClickListener {

            val email = findViewById<EditText>(R.id.editTextTextEmailAddress)
            val password = findViewById<EditText>(R.id.editTextTextPassword)

            hideKeyboard()

            progressBar.visibility = ProgressBar.VISIBLE;

            if (email.text.isBlank() || password.text.isBlank()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else
            {
                loginUser(email.text.toString(), password.text.toString());
            }


        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        val userId = user.uid
                        fetchUserData(userId)
                    }
                } else {
                    Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    progressBar.visibility = ProgressBar.INVISIBLE;
                    Log.e("LoginActivity", "Login failed", task.exception)
                }
            }
    }

    private fun fetchUserData(userId: String) {
        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val username = document.getString("username") ?: "User"
                    Toast.makeText(this, "Welcome back, $username!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish() // Finish LoginActivity to prevent returning on back press
                } else {
                    Toast.makeText(this, "User data not found", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error fetching user data", Toast.LENGTH_SHORT).show()
                Log.e("LoginActivity", "Error fetching user data", e)
            }
    }

    private fun hideKeyboard() {
        // Get the currently focused view
        val view = currentFocus
        if (view != null) {
            // Get the InputMethodManager system service
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            // Hide the keyboard
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}