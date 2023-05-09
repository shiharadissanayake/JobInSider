package com.example.jobinsider



import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class JobAdapter(private val context: Context, private var dataList: List<JobDataClass>) : RecyclerView.Adapter<JobViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.job_item, parent, false)
        return JobViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {

        holder.recTitle.text = dataList[position].jobtitle
        holder.recDesc.text = dataList[position].jobdesc
        holder.recPriority.text = dataList[position].companyname



    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun searchDataList(searchList: List<JobDataClass>) {
        dataList = searchList
        notifyDataSetChanged()
    }

}

class JobViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var recTitle: TextView
    var recDesc: TextView
    var recPriority: TextView
    var recCard: CardView

    init {

        recTitle = itemView.findViewById(R.id.jptitle)
        recDesc = itemView.findViewById(R.id.jpdesc)
        recPriority = itemView.findViewById(R.id.jpcompanyname)
        recCard = itemView.findViewById(R.id.recCard)
    }
}