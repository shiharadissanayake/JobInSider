package com.example.jobinsider

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class update_reservation : AppCompatActivity() {
    private lateinit var newDate: EditText
    private lateinit var newTime: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_reservation)

        // get a reference to the Add to Cart button
        val updateResevaation = findViewById<Button>(R.id.btnReservationUpdate)
        val close_button = findViewById<TextView>(R.id.btnCloseReservation)
        newDate = findViewById<EditText>(R.id.updateDate)
        newTime = findViewById<EditText>(R.id.updateTime)


        close_button.setOnClickListener {
            val intent = Intent(this, job_reservation::class.java)
            startActivity(intent)
        }

        newDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                newDate.setText(selectedDate)
            }, year, month, day)

            datePickerDialog.show()
        }

        // Set an OnClickListener to show the time picker dialog when the EditText is clicked
        newTime.setOnClickListener {
            // Get the current time
            val currentTime = Calendar.getInstance()
            val currentHour = currentTime.get(Calendar.HOUR_OF_DAY)
            val currentMinute = currentTime.get(Calendar.MINUTE)

            // Create a time picker dialog and set its initial time to the current time
            val timePickerDialog = TimePickerDialog(
                this,
                { _, hourOfDay, minute ->
                    // Format the selected time as a string
                    val selectedTime = String.format("%02d:%02d", hourOfDay, minute)

                    // Set the selected time to the EditText
                    newTime.setText(selectedTime)
                },
                currentHour,
                currentMinute,
                false
            )

            // Show the time picker dialog
            timePickerDialog.show()
        }


        // set an OnClickListener on the Add to Cart button
        updateResevaation.setOnClickListener {
            val postId = GlobalVariables.postId

            // Get the new date and time values
            val updatedDate = newDate.text.toString()
            val updatedTime = newTime.text.toString()


            // Update the reservation in Firebase
            val database = FirebaseDatabase.getInstance().reference
            if (postId != null) {
                database.child("reservations").child(postId).child("date").setValue(updatedDate)
                database.child("reservations").child(postId).child("time").setValue(updatedTime)

                // Show a success message
                Toast.makeText(this, "Reservation updated successfully!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, reserve_list::class.java)
                startActivity(intent)
            }
        }
    }
}