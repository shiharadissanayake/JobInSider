package com.example.jobinsider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.jobinsider.databinding.ActivityDeleteJobBinding
import com.example.jobinsider.databinding.ActivityJobProviderBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DeleteJob : AppCompatActivity() {

    private lateinit var binding: ActivityDeleteJobBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.deleteJobBtn.setOnClickListener{
            var userName = binding.deletejobtitle.text.toString()
            if (userName.isNotEmpty())
                deleteData(userName)
            else
                Toast.makeText(this,"Please Add Job Title",Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteData(userName:String){

        database = FirebaseDatabase.getInstance().getReference("Job Vacancies")
        database.child(userName).removeValue().addOnSuccessListener{

            binding.deletejobtitle.text.clear()
            Toast.makeText(this,"Successfully Deleted",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this,"Failure",Toast.LENGTH_SHORT).show()
        }
    }
}