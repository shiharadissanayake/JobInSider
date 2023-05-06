package com.example.jobinsider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.jobinsider.databinding.ActivityUpdateJobsBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateJobs : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateJobsBinding
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateJobsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.updateBtn.setOnClickListener {

            val userName = binding.userName.text.toString()
            val firstName = binding.firstName.text.toString()
            val lastName = binding.lastname.text.toString()




            updateData(userName,firstName,lastName)

        }

    }


    private fun updateData(userName: String, firstName: String, lastName: String) {

        database = FirebaseDatabase.getInstance().getReference("Job Vacancies")
        val user = mapOf<String,String>(
            "jobtitle" to firstName,
            "jobdesc" to lastName,
            "jobqualification" to userName
        )

        database.child(userName).updateChildren(user).addOnSuccessListener {

            binding.userName.text.clear()
            binding.firstName.text.clear()
            binding.lastname.text.clear()

            Toast.makeText(this,"Successfuly Updated",Toast.LENGTH_SHORT).show()


        }.addOnFailureListener{

            Toast.makeText(this,"Failed to Update",Toast.LENGTH_SHORT).show()

        }
    }
}