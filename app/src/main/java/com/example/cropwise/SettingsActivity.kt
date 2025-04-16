package com.example.cropwise

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cropwise.actionbar.mainToolBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SettingsActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private lateinit var username: TextView
    private lateinit var email: TextView

    // views
    private lateinit var toolbar : Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)

        val logout = findViewById<Button>(R.id.logout)

        // Initialize username and email properly
        username = findViewById(R.id.username)
        email = findViewById(R.id.email)

        val deleteAccount = findViewById<Button>(R.id.account_delete)

        val changeProfile = findViewById<Button>(R.id.editProfile)

        fetchUser()

        logout.setOnClickListener {
            logout()
        }

        deleteAccount.setOnClickListener {

            showDeleteAccountDialog()

        }

        changeProfile.setOnClickListener{

            editProfile();

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initToolbar()
    }

    private fun initToolbar(){
        toolbar = findViewById(R.id.toolBar_setting)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu : Menu?) : Boolean{
        menuInflater.inflate(R.menu.main_toolbar, menu)

        mainToolBar(this, toolbar)
        toolbar.title = "Settings"

        menu?.findItem(R.id.menu_item_settings)?.setVisible(false)
        menu?.findItem(R.id.menu_item_rating)?.setVisible(false)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return mainToolBar.setOnOptionsItemSelected(item)
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

//    private fun accountDelete()
//    {
//        val user = auth.currentUser
//
//        if(user != null)
//        {
//            db.collection("user").document(user.uid).delete()
//                .addOnSuccessListener {
//                    user.delete().addOnSuccessListener {
//                        Toast.makeText(this, "Account deleted", Toast.LENGTH_SHORT).show()
//                        logout()
//                        finish()
//                    } .addOnFailureListener {
//                        Toast.makeText(this, "Error deleting account", Toast.LENGTH_SHORT).show()
//                    }
//
//                }
//                .addOnFailureListener{
//
//                    Toast.makeText(this, "Error deleting account", Toast.LENGTH_SHORT).show()
//
//                }
//
//        }
//    }

    private fun editProfile() {
        val intent = Intent(this, EditProfileActivity::class.java);
        startActivity(intent);
    }

    private fun confirmAccountDelete(password: String) {
        val user = auth.currentUser

        if (user != null && user.email != null) {
            val credential = com.google.firebase.auth.EmailAuthProvider.getCredential(user.email!!, password)

            user.reauthenticate(credential)
                .addOnSuccessListener {
                    // User authenticated successfully, now delete
                    db.collection("users").document(user.uid).delete()
                        .addOnSuccessListener {
                            user.delete()
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Account deleted successfully", Toast.LENGTH_SHORT).show()
                                    logout()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(this, "Error deleting account: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Error deleting user data: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Authentication failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
        }
    }


    private fun showDeleteAccountDialog() {
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Confirm Account Deletion")

        val layout = android.widget.LinearLayout(this)
        layout.orientation = android.widget.LinearLayout.VERTICAL
        layout.setPadding(50, 40, 50, 10)

        val passwordInput = android.widget.EditText(this)
        passwordInput.hint = "Enter your Password"
        passwordInput.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        layout.addView(passwordInput)

        builder.setView(layout)

        builder.setPositiveButton("Delete") { _, _ ->
            val password = passwordInput.text.toString()

            if (password.isEmpty()) {
                Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                confirmAccountDelete(password)
            }
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

}
