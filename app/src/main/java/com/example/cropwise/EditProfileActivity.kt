package com.example.cropwise

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class EditProfileActivity : AppCompatActivity() {

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val user = auth.currentUser;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_profile)

        val buttonSave = findViewById<Button>(R.id.buttonSave)
        val editTextName = findViewById<EditText>(R.id.username)

        val changePassword = findViewById<Button>(R.id.changePassword)
        val changeEmail = findViewById<Button>(R.id.changeEmail)

        val userRef = db.collection("users").document(user!!.uid);

        buttonSave.isEnabled = false;



        userRef.get().addOnSuccessListener { document->

            val originalUsername = document.getString("username");


            editTextName.setText(document.getString("username"))


            val textWatcher = object : TextWatcher {

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val currentUsername = editTextName.text.toString()
                    buttonSave.isEnabled =
                        currentUsername != originalUsername
                }

                override fun afterTextChanged(s: Editable?) {}



            }



            editTextName.addTextChangedListener(textWatcher)


        }

        buttonSave.setOnClickListener {

            val newName = editTextName.text.toString()

            editUser(newName);
        }

        changePassword.setOnClickListener {



        }

        changeEmail.setOnClickListener {
            showChangeEmailDialog()
        }

        changePassword.setOnClickListener{

            showChangePasswordDialog();

        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun editUser(username:String)
    {
        if(user != null)
        {
            val userRef = db.collection("users").document(user.uid);

            userRef.update("username",username).addOnSuccessListener {
                Toast.makeText(this, "Username updated", Toast.LENGTH_SHORT).show();
            }.addOnFailureListener{

                Toast.makeText(this, "Error updating username", Toast.LENGTH_SHORT).show();

            };


        }
    }

    private fun showChangeEmailDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Change Email")

        val input = EditText(this)
        input.hint = "Enter new email"
        input.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        input.setPadding(40, 20, 40, 20)

        builder.setView(input)

        builder.setPositiveButton("Submit") { dialog, _ ->
            val newEmail = input.text.toString().trim()
            if (newEmail.isNotEmpty()) {
                updateEmail(newEmail)
            } else {
                Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    fun updateEmail(email:String)
    {
        if(user != null)
        {
            val userRef = db.collection("users").document(user.uid);
            user.verifyBeforeUpdateEmail(email)
                .addOnSuccessListener {
                    // Also update Firestore to keep it consistent
                    userRef.update("email", email)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Check your new email for verification link", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Firestore email update failed", Toast.LENGTH_SHORT).show()
                        }
                }
                .addOnFailureListener { e ->
                    Log.d("Error", e.message.toString());
                    Toast.makeText(this, "Error updating email in Authentication: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }

    }

    private fun changePassword(newPassword:String)
    {
        val user = auth.currentUser

        if (user != null) {
            user.updatePassword(newPassword)
                .addOnSuccessListener {
                    Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show()
                    auth.signOut();

                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Clears back stack
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error updating password: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showChangePasswordDialog() {
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Change Password")

        val layout = android.widget.LinearLayout(this)
        layout.orientation = android.widget.LinearLayout.VERTICAL
        layout.setPadding(50, 40, 50, 10)

        val newPasswordInput = android.widget.EditText(this)
        newPasswordInput.hint = "Enter New Password"
        newPasswordInput.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        layout.addView(newPasswordInput)

        val confirmPasswordInput = android.widget.EditText(this)
        confirmPasswordInput.hint = "Confirm New Password"
        confirmPasswordInput.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        layout.addView(confirmPasswordInput)

        builder.setView(layout)

        builder.setPositiveButton("Submit") { _, _ ->
            val newPassword = newPasswordInput.text.toString()
            val confirmPassword = confirmPasswordInput.text.toString()

            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill both fields", Toast.LENGTH_SHORT).show()
            } else if (newPassword != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                changePassword(newPassword)
            }
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }
}