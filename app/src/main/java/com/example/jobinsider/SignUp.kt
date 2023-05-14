package com.example.jobinsider


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.jobinsider.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()


        binding.signupButton.setOnClickListener{

            val email = binding.signupEmail.text.toString()
            val password = binding.signupPassword.text.toString()
            val confirmPassword = binding.signupConfirm.text.toString()
            val phoneno = binding.signupPhoneno.text.toString()

            val currentUser = firebaseAuth.currentUser

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()){
                if (password == confirmPassword){
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
                        if (it.isSuccessful){
                            val intent = Intent(this, LogIn::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Password does not matched", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }




            if (currentUser != null) {



                val username = binding.signupName.text.toString()

                val phoneno = binding.signupPhoneno.text.toString()


                database = FirebaseDatabase.getInstance().getReference("Users")
                val user= UserData(username,phoneno)
                database.child(currentUser.uid).setValue(user).addOnSuccessListener {

                    binding.signupName.text.clear()

                    binding.signupPhoneno.text.clear()

                    Toast.makeText(this,"Successfully Saved", Toast.LENGTH_SHORT).show()

                }.addOnFailureListener{

                    Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()


                }




            }

        }
        binding.loginRedirectText.setOnClickListener {
            val loginIntent = Intent(this, LogIn::class.java)
            startActivity(loginIntent)
        }
    }
}