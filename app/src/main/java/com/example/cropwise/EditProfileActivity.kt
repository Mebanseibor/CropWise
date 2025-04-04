package com.example.cropwise

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
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
//        val editPassword = findViewById<EditText>(R.id.password)

        val userRef = db.collection("users").document(user!!.uid);

        buttonSave.isEnabled = false;

        userRef.get().addOnSuccessListener { document->

            val originalUsername = document.getString("username");
            val originalEmail = document.getString("email");

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

//            user.verifyBeforeUpdateEmail(email)
//                .addOnSuccessListener {
//                    // Also update Firestore to keep it consistent
//                    userRef.update("email", email)
//                        .addOnSuccessListener {
//                            Toast.makeText(this, "Email updated successfully", Toast.LENGTH_SHORT).show()
//                        }
//                        .addOnFailureListener {
//                            Toast.makeText(this, "Firestore email update failed", Toast.LENGTH_SHORT).show()
//                        }
//                }
//                .addOnFailureListener { e ->
//                    Toast.makeText(this, "Error updating email in Authentication: ${e.message}", Toast.LENGTH_SHORT).show()
//                }
        }
    }
}