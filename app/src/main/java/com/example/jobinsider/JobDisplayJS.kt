package com.example.jobinsider

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobinsider.databinding.ActivityJobDisplayJsBinding

import com.google.firebase.database.*

class JobDisplayJS : AppCompatActivity() {

    private lateinit var jsdbref : DatabaseReference
    private lateinit var jsjobRecyclerview : RecyclerView
    private lateinit var jsjobArrayList : ArrayList<JobData>

    private lateinit var binding: ActivityJobDisplayJsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_display_js)
        binding = ActivityJobDisplayJsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        jsjobRecyclerview = findViewById(R.id.JobList)
        jsjobRecyclerview.layoutManager = LinearLayoutManager(this)
        jsjobRecyclerview.setHasFixedSize(true)

        jsjobArrayList = arrayListOf<JobData>()
        getUserData()




    }

    private fun getUserData() {

        jsdbref = FirebaseDatabase.getInstance().getReference("Job Vacancies")

        jsdbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){


                        val user = userSnapshot.getValue(JobData::class.java)
                        jsjobArrayList.add(user!!)

                    }

                    jsjobRecyclerview.adapter = MyAdapter(jsjobArrayList)




                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }


}