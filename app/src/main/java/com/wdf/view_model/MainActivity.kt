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
        val resultText = findViewById<TextView>(R.id.resultText)

        // Observe LiveData and update UI when the converted time changes
        clockViewModel.convertedTime.observe(this) { convertedTime ->
            resultText.text = convertedTime
        }

        // Convert timestamp when the button is clicked
        convertButton.setOnClickListener {
            val timestamp = timestampInput.text.toString().toLongOrNull()
            if (timestamp != null) {
                clockViewModel.convertTime(timestamp)
            } else {
                resultText.text = getString(R.string.error_invalid_timestamp)
            }
        }
    }
}


