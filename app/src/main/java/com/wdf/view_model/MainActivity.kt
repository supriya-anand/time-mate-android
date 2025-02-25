package com.wdf.view_model

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private val clockViewModel: ClockViewModel by viewModels() // ViewModel instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timestampInput = findViewById<EditText>(R.id.timestampInput)
        val convertButton = findViewById<Button>(R.id.convertButton)
        val humanDateResult = findViewById<TextView>(R.id.humanDateResult)

        // New input fields for human-readable date
        val dayInput = findViewById<EditText>(R.id.dayInput)
        val monthInput = findViewById<EditText>(R.id.monthInput)
        val yearInput = findViewById<EditText>(R.id.yearInput)
        val hourInput = findViewById<EditText>(R.id.hourInput)
        val minuteInput = findViewById<EditText>(R.id.minuteInput)
        val secondInput = findViewById<EditText>(R.id.secondInput)
        val convertHumanTimeButton = findViewById<Button>(R.id.convertHumanTimeButton)
        val unixTimestampResult = findViewById<TextView>(R.id.unixTimestampResult) // Result for human date -> timestamp


        // Observe LiveData and update UI when the converted time changes
        clockViewModel.convertedTime.observe(this) { convertedTime ->
            humanDateResult.text = convertedTime
        }

        clockViewModel.unixTime.observe(this) { unixTime ->
            unixTimestampResult.text = String.format(unixTime.toString())
        }

        // Convert timestamp when the button is clicked
        convertButton.setOnClickListener {
            val timestamp = timestampInput.text.toString().toLongOrNull()
            if (timestamp != null) {
                clockViewModel.convertTime(timestamp)
            } else {
                humanDateResult.text = getString(R.string.error_invalid_timestamp)
            }
        }

        // Convert human-readable date to timestamp
        convertHumanTimeButton.setOnClickListener {
            val day = dayInput.text.toString().toIntOrNull()
            val month = monthInput.text.toString().toIntOrNull()
            val year = yearInput.text.toString().toIntOrNull()
            val hour = hourInput.text.toString().toIntOrNull() ?: 0
            val minute = minuteInput.text.toString().toIntOrNull() ?: 0
            val second = secondInput.text.toString().toIntOrNull() ?: 0

            if (day != null && month != null && year != null) {
                val formattedDate = String.format("%04d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, minute, second)
                clockViewModel.backToUnix(formattedDate)
            } else {
                unixTimestampResult.text = getString(R.string.error_invalid_date)
            }
        }
    }
}


