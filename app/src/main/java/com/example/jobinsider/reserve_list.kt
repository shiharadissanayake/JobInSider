package com.example.jobinsider

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class reserve_list : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ReservationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve_list)

        recyclerView = findViewById(R.id.recyclerViewReservation)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val close_button = findViewById<TextView>(R.id.btnJRLBack)
        close_button.setOnClickListener {
            val intent = Intent(this, reserve_list::class.java)
            startActivity(intent)
        }

        val database = FirebaseDatabase.getInstance().reference
        val reservations = mutableListOf<Reservation>()
        adapter = ReservationAdapter(reservations)
        recyclerView.adapter = adapter

        database.child("reservations").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                reservations.clear()
                for (postSnapshot in dataSnapshot.children) {
                    val reservation = postSnapshot.getValue(Reservation::class.java)
                    reservation?.let {
                        reservations.add(it)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(ContentValues.TAG, "Failed to read value.", databaseError.toException())
            }
        })
    }


    data class Reservation(
        val fullName: String = "",
        val tel: String = "",
        val date: String = "",
        val time: String = "",
        val jobProvider: String = "",
        val postId: String?
    ){
        constructor() : this("", "", "", "", "", "")
    }

}
