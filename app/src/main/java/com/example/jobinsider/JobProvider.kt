package com.example.jobinsider

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import com.example.jobinsider.databinding.ActivityJobProviderBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class JobProvider : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var jobRecyclerview : RecyclerView
    private lateinit var jobArrayList : ArrayList<JobData>

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    lateinit var toogle : ActionBarDrawerToggle



    private lateinit var binding: ActivityJobProviderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobProviderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference


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

        binding.deleteJobButton.setOnClickListener {

            val intent = Intent(this, DeleteJob::class.java)
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


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.delete_user ->{
                delete_user()
                return true
            }
            R.id.update_user ->{
                startActivity(Intent(this,UpdateUserActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

        return true

        if(toogle.onOptionsItemSelected(item)){

            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun delete_user() {
        val currentUser = auth.currentUser
        if(currentUser != null){
            val userRef = database.child("users").child(currentUser.uid)
            userRef.removeValue()
            currentUser.delete()
                .addOnSuccessListener {
                    Toast.makeText(this,"User deleted Successfully",Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener{
                    Toast.makeText(this,"Error deleteing user",Toast.LENGTH_SHORT).show()
                }
        }else{
            Toast.makeText(this,"User not found",Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.option_menu,menu)

        return super.onCreateOptionsMenu(menu)

}
    private fun update_user(email: String, password: String){
        val currentUser = auth.currentUser
        if(currentUser != null){
            val userUpdates = HashMap<String, Any>()
            userUpdates["email"] = email
            userUpdates["password"] = password

            val userRef = database.child("users").child(currentUser.uid)
            userRef.updateChildren(userUpdates)
                .addOnSuccessListener {
                    Toast.makeText(this,"User updated Successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{
                    Toast.makeText(this,"Error updating user",Toast.LENGTH_SHORT).show()
                }
        }else{
            Toast.makeText(this,"User not found",Toast.LENGTH_SHORT).show()
            }

        }



}