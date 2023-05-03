package com.example.jobinsider

import android.content.Intent
import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import com.example.jobinsider.databinding.ActivityJobProviderBinding
import com.google.firebase.database.*


class JobProvider : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var jobRecyclerview : RecyclerView
    private lateinit var jobArrayList : ArrayList<JobData>


    private lateinit var binding: ActivityJobProviderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobProviderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        jobRecyclerview = findViewById(R.id.userList)
        jobRecyclerview.layoutManager = LinearLayoutManager(this)
        jobRecyclerview.setHasFixedSize(true)

        jobArrayList = arrayListOf<JobData>()
        getUserData()


        binding.fab.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@JobProvider, AddJob::class.java)
            startActivity(intent)
        })

        binding.updateJobButton.setOnClickListener {

            val intent = Intent(this, UpdateJobs::class.java)
            startActivity(intent)

        }





    }

    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("Job Vacancies")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){


                        val user = userSnapshot.getValue(JobData::class.java)
                        jobArrayList.add(user!!)

                    }

                    jobRecyclerview.adapter = MyAdapter(jobArrayList)




                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }

}