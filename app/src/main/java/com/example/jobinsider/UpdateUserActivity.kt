package com.example.jobinsider

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class UpdateUserActivity : AppCompatActivity() {



    private lateinit var nameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var saveButton: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        nameEditText = findViewById(R.id.edit_text_new_email)
        passwordEditText = findViewById(R.id.edit_text_new_password)
//        saveButton = findViewById(R.id.button_update)

        saveButton.setOnClickListener{
            update_user()
        }
    }

    private fun update_user() {
        val currentUser = auth.currentUser
        if(currentUser != null){
            val newemail = nameEditText.text.toString().trim()
            val newPassword = passwordEditText.text.toString().trim()

            if(newemail.isEmpty()){
                nameEditText.error = "Email is required"
                nameEditText.requestFocus()
                return
            }

            if(newPassword.isEmpty()){
                passwordEditText.error = "Password is required"
                passwordEditText.requestFocus()
                return
            }

            val userUpdates = HashMap<String, Any>()
            userUpdates["email"] = newemail
            userUpdates["password"] = newPassword



            val userRef = database.child("Users").child(currentUser.uid)
            userRef.updateChildren(userUpdates)
                .addOnSuccessListener {

                    Toast.makeText(this,"User updated successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener{
                    Toast.makeText(this,"Error in updating user", Toast.LENGTH_SHORT).show()
                }
        }else{
            Toast.makeText(this,"User not found", Toast.LENGTH_SHORT).show()
        }
    }
}                   //UpdateUserActivity