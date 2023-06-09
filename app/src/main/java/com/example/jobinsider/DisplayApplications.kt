package com.example.jobinsider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.jobinsider.databinding.ActivityDisplayApplicationsBinding
import com.example.jobinsider.databinding.ActivityJobProviderBinding
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList


private lateinit var binding: ActivityDisplayApplicationsBinding
var databaseReference: DatabaseReference? = null
var eventListener: ValueEventListener? = null
private lateinit var dataList: ArrayList<DataClass>
private lateinit var adapter: MyAdapter

class DisplayApplications : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_applications)

        binding = ActivityDisplayApplicationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gridLayoutManager = GridLayoutManager(this@DisplayApplications, 1)
        binding.recyclerView.layoutManager = gridLayoutManager
        binding.search.clearFocus()
        val builder = AlertDialog.Builder(this@DisplayApplications)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()
        dataList = ArrayList()
        adapter = MyAdapter(this@DisplayApplications, dataList)
        binding.recyclerView.adapter = adapter
        databaseReference = FirebaseDatabase.getInstance().getReference("Job Applications")
        dialog.show()
        eventListener = databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (itemSnapshot in snapshot.children) {
                    val dataClass = itemSnapshot.getValue(DataClass::class.java)
                    if (dataClass != null) {
                        dataList.add(dataClass)
                    }
                }
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            override fun onCancelled(error: DatabaseError) {
                dialog.dismiss()
            }
        })

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                searchList(newText)
                return true
            }
        })
    }
    fun searchList(text: String) {
        val searchList = java.util.ArrayList<DataClass>()
        for (dataClass in dataList) {
            if (dataClass.jobposition?.lowercase()
                    ?.contains(text.lowercase(Locale.getDefault())) == true
            ) {
                searchList.add(dataClass)
            }
        }
        adapter.searchDataList(searchList)
    }

    }

