package com.example.jobinsider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class jobApplicationAdapter(private val jobapplicationList : ArrayList<JobApplications>) : RecyclerView.Adapter<jobApplicationAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_display_applications,
            parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = jobapplicationList[position]

        holder.firstName.text = currentitem.jobposition
        holder.lastName.text = currentitem.town
        holder.age.text = currentitem.time
    }



    override fun getItemCount(): Int {

        return jobapplicationList.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val firstName : TextView = itemView.findViewById(R.id.jsjobPosition)
        val lastName : TextView = itemView.findViewById(R.id.jstown)
        val age : TextView = itemView.findViewById(R.id.jstime)

    }





}

