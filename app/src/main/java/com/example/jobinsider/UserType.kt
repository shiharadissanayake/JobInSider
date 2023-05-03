package com.example.jobinsider

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jobinsider.databinding.ActivityLogInBinding
import com.example.jobinsider.databinding.ActivityUserTypeBinding

class UserType : AppCompatActivity() {

    private lateinit var binding: ActivityUserTypeBinding

    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_type)

        binding = ActivityUserTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.jobproviderType.setOnClickListener {

            val intent = Intent(this, JobProvider::class.java)
            startActivity(intent)

        }

        binding.jobseekerType.setOnClickListener {

            val intent = Intent(this, JobSeeker::class.java)
            startActivity(intent)

        }


    }
}