package com.example.jobinsider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.jobinsider.databinding.ActivityEditJobApplicationsBinding
import com.example.jobinsider.databinding.ActivityUpdateJobsBinding

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditJobApplications : AppCompatActivity() {

    private lateinit var binding: ActivityEditJobApplicationsBinding
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditJobApplicationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.updateBtn.setOnClickListener {

            val userName = binding.updateJsfullname.text.toString()
            val town = binding.updateTown.text.toString()
            val time = binding.updateTime.text.toString()


            updateData(userName,town,time)

        }

    }


    private fun updateData(userName: String, town: String, time: String) {

        database = FirebaseDatabase.getInstance().getReference("Job Applications")
        val user = mapOf<String,String>(
            "fulllname" to userName,
            "time" to time,
            "town" to town
        )

        database.child(userName).updateChildren(user).addOnSuccessListener {

            binding.updateJsfullname.text.clear()
            binding.updateTown.text.clear()
            binding.updateTime.text.clear()

            Toast.makeText(this,"Successfuly Updated", Toast.LENGTH_SHORT).show()


        }.addOnFailureListener{

            Toast.makeText(this,"Failed to Update", Toast.LENGTH_SHORT).show()

        }
    }
}