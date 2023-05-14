package com.example.jobinsider

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class job_reservation : AppCompatActivity() {

    private lateinit var fullNameEditText: EditText
    private lateinit var telEditText: EditText
    private lateinit var dateEditText: EditText
    private lateinit var timeEditText: EditText
    private lateinit var jobProviderSpinner: Spinner
    private lateinit var reserveButton: Button
    private lateinit var viewList: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_reservation)

        // Initialize views
        fullNameEditText = findViewById(R.id.txtFullName)
        telEditText = findViewById(R.id.txtTel)
        dateEditText = findViewById(R.id.txtDate)
        timeEditText = findViewById(R.id.txtTime)
        jobProviderSpinner = findViewById(R.id.spinnerJobProvider)
        reserveButton = findViewById(R.id.btnReserve)
        viewList = findViewById(R.id.view_list_btn)


        dateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                dateEditText.setText(selectedDate)
            }, year, month, day)

            datePickerDialog.show()
        }

        // Set an OnClickListener to show the time picker dialog when the EditText is clicked
        timeEditText.setOnClickListener {
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
                    timeEditText.setText(selectedTime)
                },
                currentHour,
                currentMinute,
                false
            )

            // Show the time picker dialog
            timePickerDialog.show()
        }

        // Set on click listener for the reserve button
        viewList.setOnClickListener {
            val intent = Intent(this@job_reservation, reserve_list::class.java)
            startActivity(intent)
        }

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

            // Validate the values
            if (!isValidPhoneNumber(tel)) {
                // Telephone number is not valid, show an error message
                Toast.makeText(this, "Please enter a valid telephone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }



            // Create a new reservation object
            val reservation = Reservation(fullName, tel, date, time, jobProvider , postId)

            if (postId != null) {
                database.child("reservations").child(postId).setValue(reservation)
            }

            fullNameEditText.setText("")
            telEditText.setText("")
            dateEditText.setText("")
            timeEditText.setText("")
            jobProviderSpinner.setSelection(0)

            // Show a success message
            Toast.makeText(this, "Reservation successful!", Toast.LENGTH_SHORT).show()
        }
    }



    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val pattern = "^[+]?[0-9]{10,13}$".toRegex()
        return pattern.matches(phoneNumber)
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

