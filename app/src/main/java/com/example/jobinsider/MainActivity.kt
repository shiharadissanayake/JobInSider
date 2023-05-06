package com.example.jobinsider

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var fullNameEditText: EditText
    private lateinit var telEditText: EditText
    private lateinit var dateEditText: EditText
    private lateinit var timeEditText: EditText
    private lateinit var jobProviderSpinner: Spinner
    private lateinit var reserveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        fullNameEditText = findViewById(R.id.txtFullName)
        telEditText = findViewById(R.id.txtTel)
        dateEditText = findViewById(R.id.txtDate)
        timeEditText = findViewById(R.id.txtTime)
        jobProviderSpinner = findViewById(R.id.spinnerJobProvider)
        reserveButton = findViewById(R.id.btnReserve)

        // Set on click listener for the reserve button
        reserveButton.setOnClickListener {
            // Save the data to Firebase
            val database = FirebaseDatabase.getInstance().reference
            val postId = database.child("reservations").push().key

            // Get the values from the EditText and Spinner views
            val fullName = fullNameEditText.text.toString()
            val tel = telEditText.text.toString()
            val date = dateEditText.text.toString()
            val time = timeEditText.text.toString()
            val jobProvider = jobProviderSpinner.selectedItem.toString()

            // Create a new reservation object
            val reservation = Reservation(fullName, tel, date, time, jobProvider , postId)

            if (postId != null) {
                database.child("reservations").child(postId).setValue(reservation)
            }

            // Show a success message
            Toast.makeText(this, "Reservation successful!", Toast.LENGTH_SHORT).show()
        }
    }

    data class Reservation(
        val fullName: String = "",
        val tel: String = "",
        val date: String = "",
        val time: String = "",
        val jobProvider: String = "",
        val postId: String?
    )
}

