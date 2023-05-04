package com.example.jobinsider

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobinsider.databinding.ActivityJobSeekerBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.*

class JobSeeker : AppCompatActivity() {

    private lateinit var jsdbref : DatabaseReference
    private lateinit var jsRecyclerview : RecyclerView
    private lateinit var jsArrayList : ArrayList<JobData>


    private lateinit var binding: ActivityJobSeekerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobSeekerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        jsRecyclerview = findViewById(R.id.jobList)
        jsRecyclerview.layoutManager = LinearLayoutManager(this)
        jsRecyclerview.setHasFixedSize(true)

        jsArrayList = arrayListOf<JobData>()
        getJobData()




    }



    private fun getJobData() {

        jsdbref = FirebaseDatabase.getInstance().getReference("Job Vacancies")

        jsdbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {

                    for (userSnapshot in snapshot.children) {


                        val user = userSnapshot.getValue(JobData::class.java)
                        jsArrayList.add(user!!)


                    }

                    jsRecyclerview.adapter = MyAdapter(jsArrayList)





                }





            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }

}