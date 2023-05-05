package com.example.jobinsider

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class JobSeeker : AppCompatActivity() {

    lateinit var toogle : ActionBarDrawerToggle
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_seeker)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)
        val intent1 = Intent(this, JobDisplayJS::class.java)
        val intent2 = Intent(this, ApplyJobs::class.java)
        val intent3 = Intent(this, EditJobApplications::class.java)
        val intent4 = Intent(this, DeleteApplication::class.java)

        toogle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {

            when(it.itemId){
                R.id.nav_home -> startActivity(intent1)
                R.id.nav_applyjob -> startActivity(intent2)
                R.id.nav_update -> startActivity(intent3)
                R.id.nav_deleteApplication -> startActivity(intent4)

            }

            true
        }
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//
//        if(toogle.onOptionsItemSelected(item)){
//
//            return true
//        }
//        return super.onOptionsItemSelected(item)
//       }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.option_menu,menu)

        return super.onCreateOptionsMenu(menu)
        }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.delete_user ->{
                delete_user()
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


}