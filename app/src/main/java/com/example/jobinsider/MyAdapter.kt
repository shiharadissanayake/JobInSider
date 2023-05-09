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

class MyAdapter(private val context: Context, private var dataList: List<DataClass>) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.recTitle.text = dataList[position].fulllname
        holder.recDesc.text = dataList[position].time
        holder.recPriority.text = dataList[position].town



    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var recTitle: TextView
    var recDesc: TextView
    var recPriority: TextView
    var recCard: CardView

    init {

        recTitle = itemView.findViewById(R.id.recTitle)
        recDesc = itemView.findViewById(R.id.recDesc)
        recPriority = itemView.findViewById(R.id.recPriority)
        recCard = itemView.findViewById(R.id.recCard)
    }
}