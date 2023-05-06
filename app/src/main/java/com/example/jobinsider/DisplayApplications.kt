package com.example.jobinsider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobinsider.databinding.ActivityDisplayApplicationsBinding

import com.google.firebase.database.*

private lateinit var jsapplicationdbref : DatabaseReference
private lateinit var jsjobapplicationRecyclerview : RecyclerView
private lateinit var jsjobapplicationArrayList : ArrayList<JobApplications>

private lateinit var binding: ActivityDisplayApplicationsBinding


class DisplayApplications : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityDisplayApplicationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        jsjobapplicationRecyclerview = findViewById(R.id.JobApplicationList)
        jsjobapplicationRecyclerview.layoutManager = LinearLayoutManager(this)
        jsjobapplicationRecyclerview.setHasFixedSize(true)

        jsjobapplicationArrayList = arrayListOf<JobApplications>()
        getApplicationData()

    }

    private fun getApplicationData() {

        jsapplicationdbref = FirebaseDatabase.getInstance().getReference("Job Applications")

        jsapplicationdbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (ApplicationSnapshot in snapshot.children){


                        val application = ApplicationSnapshot.getValue(JobApplications::class.java)
                        jsjobapplicationArrayList.add(application!!)

                    }

                    jsjobapplicationRecyclerview.adapter = jobApplicationAdapter(jsjobapplicationArrayList)




                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }
}