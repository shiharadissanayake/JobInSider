package com.example.jobinsider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.jobinsider.databinding.ActivityDeleteApplicationBinding
import com.example.jobinsider.databinding.ActivityDeleteJobBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DeleteApplication : AppCompatActivity() {

    private lateinit var binding: ActivityDeleteApplicationBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteApplicationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.deleteApplicationBtn.setOnClickListener{
            var userName = binding.deleteapplication.text.toString()
            if (userName.isNotEmpty())
                deleteData(userName)
            else
                Toast.makeText(this,"Please Add Job Title", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteData(userName:String){

        database = FirebaseDatabase.getInstance().getReference("Job Applications")
        database.child(userName).removeValue().addOnSuccessListener{

            binding.deleteapplication.text.clear()
            Toast.makeText(this,"Successfully Deleted", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this,"Failure", Toast.LENGTH_SHORT).show()
        }
    }
}