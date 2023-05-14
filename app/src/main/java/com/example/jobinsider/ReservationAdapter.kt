package com.example.jobinsider

import android.content.ContentValues
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*


class ReservationAdapter(private val reservations: List<reserve_list.Reservation>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_one_item_resevation, parent, false)
        return ReservationViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val reservation = reservations[position]
        val reservationViewHolder = holder as ReservationViewHolder
        reservationViewHolder.bind(reservation)
    }

    override fun getItemCount(): Int {
        return reservations.size
    }

    inner class ReservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val remainingTime: TextView = itemView.findViewById(R.id.lbl_remaining_time)
        private val appoinmentTime: TextView = itemView.findViewById(R.id.lbl_appointment_time)
        private val appoinmentDate: TextView = itemView.findViewById(R.id.lbl_appointment_Date)
        private val jobProvider: TextView = itemView.findViewById(R.id.lbl_provider)

        private val context = itemView.context
        var deleteButton: Button? = null
        var updateButton: Button? = null

        fun bind(reservation: reserve_list.Reservation) {

            appoinmentTime.text = "Reservation Time : "+reservation.time
            appoinmentDate.text = "Reservation Date : "+reservation.date
            jobProvider.text = "Job Provider : "+reservation.jobProvider


            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())

            // Parse the dates
            val date1 = dateFormat.parse("12/05/2023 14:30:00")
            val date2 = dateFormat.parse("14/05/2023 10:15:30")

            // Calculate the difference between the dates in milliseconds
            val diffMillis = date2.time - date1.time

            // Convert the milliseconds to hours, minutes, and seconds
            val diffSeconds = diffMillis / 1000
            val diffMinutes = diffSeconds / 60
            val diffHours = diffMinutes / 60

            // Calculate the remaining minutes and seconds
            val remainingMinutes = diffMinutes % 60
            val remainingSeconds = diffSeconds % 60

            // Format the difference as "hh:mm:ss"
            val diffString = String.format("%02d:%02d:%02d", diffHours, remainingMinutes, remainingSeconds)
            var remainingTimeInMinutes =diffString;

            // Set the remaining time text
            remainingTime.text = if (remainingTimeInMinutes > "0") {
                "Remaining time: $remainingTimeInMinutes minutes"
            } else {
                "Time's up!"
            }


            updateButton = itemView.findViewById<Button>(R.id.editBtn)
            updateButton?.setOnClickListener {
                val intent = Intent(context, update_reservation::class.java)

                GlobalVariables.postId = reservation.postId.toString();
                GlobalVariables.tel = reservation.tel.toString();
                GlobalVariables.date = reservation.date.toString();
                GlobalVariables.date = reservation.date.toString();
                GlobalVariables.time = reservation.time.toString();
                context.startActivity(intent)
            }

            deleteButton = itemView.findViewById<Button>(R.id.deleteBtn)
            deleteButton?.setOnClickListener {
                // Assume that you have a reference to your Firebase Database root
                val databaseRef = FirebaseDatabase.getInstance().reference

                // Assume that you have a reference to the reservation that you want to delete
                val reservationRef = databaseRef.child("reservations").child(reservation.postId.toString())

                // Call the removeValue() method on the reservation reference to delete it
                reservationRef.removeValue().addOnSuccessListener {
                    Toast.makeText(context, "Reservation deleted successfully", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener { e ->
                    Log.e(ContentValues.TAG, "Error deleting reservation", e)
                    Toast.makeText(context, "Failed to delete reservation", Toast.LENGTH_SHORT).show()
                }
            }
        }



    }
}
