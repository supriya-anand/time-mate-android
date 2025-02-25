package com.wdf.view_model

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.widget.Switch
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var switchTheme: Switch
    private lateinit var sharedPreferences: SharedPreferences
    private val clockViewModel: ClockViewModel by viewModels() // ViewModel instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        applySavedTheme()

        setContentView(R.layout.activity_main)

        initializeUI()

    }

    private fun applySavedTheme() {
        sharedPreferences = getSharedPreferences("ThemePrefs", MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("darkMode", false)

        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun initializeUI() {
        val timestampInput = findViewById<EditText>(R.id.timestampInput)
        val convertButton = findViewById<Button>(R.id.convertButton)
        val humanDateResult = findViewById<TextView>(R.id.humanDateResult)

        val dayInput = findViewById<EditText>(R.id.dayInput)
        val monthInput = findViewById<EditText>(R.id.monthInput)
        val yearInput = findViewById<EditText>(R.id.yearInput)
        val hourInput = findViewById<EditText>(R.id.hourInput)
        val minuteInput = findViewById<EditText>(R.id.minuteInput)
        val secondInput = findViewById<EditText>(R.id.secondInput)
        val convertHumanTimeButton = findViewById<Button>(R.id.convertHumanTimeButton)
        val unixTimestampResult = findViewById<TextView>(R.id.unixTimestampResult)

        switchTheme = findViewById(R.id.switchTheme)

        // Set initial state of theme switch
        switchTheme.isChecked = sharedPreferences.getBoolean("darkMode", false)

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            toggleTheme(isChecked)
        }

        // Observe ViewModel data
        clockViewModel.convertedTime.observe(this) { humanDateResult.text = it }
        clockViewModel.unixTime.observe(this) { unixTimestampResult.text = it.toString() }

        // Convert timestamp button
        convertButton.setOnClickListener {
            val timestamp = timestampInput.text.toString().toLongOrNull()
            humanDateResult.text = timestamp?.let { clockViewModel.convertTime(it); "" }
                ?: getString(R.string.error_invalid_timestamp)
        }

        // Convert human-readable date to timestamp
        convertHumanTimeButton.setOnClickListener {
            val formattedDate = getFormattedDate(dayInput, monthInput, yearInput, hourInput, minuteInput, secondInput)
            unixTimestampResult.text = formattedDate?.let { clockViewModel.backToUnix(it); "" }
                ?: getString(R.string.error_invalid_date)
        }
    }

    private fun toggleTheme(isDarkMode: Boolean) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        sharedPreferences.edit().putBoolean("darkMode", isDarkMode).apply()
    }

    private fun getFormattedDate(
        dayInput: EditText, monthInput: EditText, yearInput: EditText,
        hourInput: EditText, minuteInput: EditText, secondInput: EditText
    ): String? {
        val day = dayInput.text.toString().toIntOrNull()
        val month = monthInput.text.toString().toIntOrNull()
        val year = yearInput.text.toString().toIntOrNull()
        val hour = hourInput.text.toString().toIntOrNull() ?: 0
        val minute = minuteInput.text.toString().toIntOrNull() ?: 0
        val second = secondInput.text.toString().toIntOrNull() ?: 0

        return if (day != null && month != null && year != null) {
            String.format(Locale.US,"%04d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, minute, second)
        } else {
            null
        }
    }

}

