package com.example.jobinsider

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
//import com.example.firebaserecyclerviewkotlin.JobAdapter
import com.example.jobinsider.databinding.ActivityJobProviderBinding
import com.example.jobinsider.databinding.ActivityUserTypeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap



class JobProvider : AppCompatActivity() {

//    private lateinit var dbref : DatabaseReference
//    private lateinit var jobRecyclerview : RecyclerView
//    private lateinit var jobArrayList : ArrayList<JobData>

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var NewEmail: EditText
    private lateinit var NewPassword: EditText


//    private lateinit var searchView: SearchView
//    private lateinit var searchList: ArrayList<JobData>

    lateinit var toogle : ActionBarDrawerToggle



    private lateinit var binding: ActivityJobProviderBinding
    var databaseReference: DatabaseReference? = null
    var eventListener: ValueEventListener? = null
    private lateinit var dataList: ArrayList<DataClass>
    private lateinit var adapter: MyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_provider)

        binding = ActivityJobProviderBinding.inflate(layoutInflater)
        setContentView(binding.root)
//
//            val gridLayoutManager = GridLayoutManager(this@JobProvider, 1)
//        binding.recyclerView.layoutManager = gridLayoutManager
//        binding.search.clearFocus()
//                val builder = AlertDialog.Builder(this@JobProvider)
//        builder.setCancelable(false)
//        builder.setView(R.layout.progress_layout)
//        val dialog = builder.create()
//        dialog.show()
//        dataList = ArrayList()
//        adapter = MyAdapter(this@JobProvider, dataList)
//        binding.recyclerView.adapter = adapter
//        databaseReference = FirebaseDatabase.getInstance().getReference("Job Application")
//        dialog.show()
//        eventListener = databaseReference!!.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                dataList.clear()
//                for (itemSnapshot in snapshot.children) {
//                    val dataClass = itemSnapshot.getValue(DataClass::class.java)
//                    if (dataClass != null) {
//                        dataList.add(dataClass)
//                    }
//                }
//                adapter.notifyDataSetChanged()
//                dialog.dismiss()
//            }
//            override fun onCancelled(error: DatabaseError) {
//                dialog.dismiss()
//            }
//        })


        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference


//        jobRecyclerview = findViewById(R.id.userList)
//        jobRecyclerview.layoutManager = LinearLayoutManager(this)
//        jobRecyclerview.setHasFixedSize(true)

//        searchView = findViewById(R.id.search)

//        jobArrayList = arrayListOf<JobData>()
//        searchList = arrayListOf<JobData>()
//        getUserData()

//        searchView.clearFocus()
//        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                searchView.clearFocus()
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                searchList.clear()
//                val searchText = newText!!.toLowerCase(Locale.getDefault())
//                if (searchText.isNotEmpty()){
//                    jobArrayList.forEach{
//                        if (it.jobtitle?.toLowerCase(Locale.getDefault())!!.contains(searchText)) {
//                            searchList.add(it)
//                        }
//                    }
//                    jobRecyclerview.adapter!!.notifyDataSetChanged()
//                } else {
//                    searchList.clear()
//                    searchList.addAll(jobArrayList)
//                    jobRecyclerview.adapter!!.notifyDataSetChanged()
//                }
//                return false
//            }
//        })


        binding.fab.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@JobProvider, AddJob::class.java)
            startActivity(intent)
        })
//
        binding.updateJobButton.setOnClickListener {

            val intent = Intent(this, UpdateJobs::class.java)
            startActivity(intent)

        }

        binding.deleteJobButton.setOnClickListener {

            val intent = Intent(this, DeleteJob::class.java)
            startActivity(intent)

        }





    }

//    private fun getUserData() {
//
//        dbref = FirebaseDatabase.getInstance().getReference("Job Vacancies")
//
//        dbref.addValueEventListener(object : ValueEventListener{
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                if (snapshot.exists()){
//
//                    for (userSnapshot in snapshot.children){
//
//
//                        val user = userSnapshot.getValue(JobData::class.java)
//                        jobArrayList.add(user!!)
//
//                    }
//
//                    jobRecyclerview.adapter = JobAdapter(jobArrayList)
//
//
//                }
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//
//        })
//
//    }





//    private fun getUserData() {
//
//        dbref = FirebaseDatabase.getInstance().getReference("Job Vacancies")
//
//        dbref.addValueEventListener(object : ValueEventListener {
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                if (snapshot.exists()){
//
//                    for (userSnapshot in snapshot.children){
//
//
//                        val user = userSnapshot.getValue(JobData::class.java)
//                        jobArrayList.add(user!!)
//
//                    }
//                    searchList.addAll(jobArrayList)
//
//                    jobRecyclerview.adapter = MyAdapter(searchList)
//
//
//
//
//                }
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//
//        })
//
//    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.delete_user ->{
                delete_user()
                return true
            }
            R.id.update_user ->{
                startActivity(Intent(this,UpdateUserActivity::class.java))
                update_user()
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
                    Toast.makeText(this,"User deleted Successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener{
                    Toast.makeText(this,"Error deleteing user",Toast.LENGTH_SHORT).show()
                }
        }else{
            Toast.makeText(this,"User not found",Toast.LENGTH_SHORT).show()
        }



        database = FirebaseDatabase.getInstance().getReference("Users")
        if (currentUser != null) {
            database.child(currentUser.uid).removeValue().addOnSuccessListener{
                Toast.makeText(this,"Successfully Deleted",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this,"Failure",Toast.LENGTH_SHORT).show()
            }
        }


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.option_menu,menu)

        return super.onCreateOptionsMenu(menu)

}
    private fun update_user() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val uid = currentUser.uid
            val userUpdates = HashMap<String, Any>()

            userUpdates["username"] = NewEmail
            userUpdates["phone"] = NewPassword

            val userRef = database.child("Users").child(uid)
            userRef.updateChildren(userUpdates)
                .addOnSuccessListener {
                    Toast.makeText(this, "User updated successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error in updating user", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
            }

       }



}


