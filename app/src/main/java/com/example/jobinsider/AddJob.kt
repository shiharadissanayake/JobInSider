package com.example.jobinsider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.jobinsider.databinding.ActivityAddJobBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddJob : AppCompatActivity() {
    private lateinit var binding : ActivityAddJobBinding
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerBtn.setOnClickListener {

            val jobtitle = binding.jobtitle.text.toString()
            val jobdesc = binding.jobdesc.text.toString()
            val jobqualification = binding.jobqualification.text.toString()
            val companyname = binding.CompanyName.text.toString()

            if(jobtitle.isEmpty()){
                Toast.makeText(this,"Job title is required",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(companyname.isEmpty()){
                Toast.makeText(this,"Company Name is required",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            database = FirebaseDatabase.getInstance().getReference("Job Vacancies")
            val Job = JobData(jobtitle,jobdesc,jobqualification,companyname)
            database.child(jobtitle).setValue(Job).addOnSuccessListener {

                binding.jobtitle.text.clear()
                binding.jobdesc.text.clear()
                binding.jobqualification.text.clear()
                binding.CompanyName.text.clear()

                Toast.makeText(this,"Successfully Saved", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener{

                Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()


            }


        }

    }




}