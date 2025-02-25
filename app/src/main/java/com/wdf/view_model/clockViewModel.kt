package com.wdf.view_model


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class ClockViewModel: ViewModel() {

    private val _convertedTime = MutableLiveData<String>()
    private val _originalTime = MutableLiveData<Long>()
    val convertedTime: LiveData<String> = _convertedTime
    val unixTime: LiveData<Long> = _originalTime

    fun convertTime(timestamp: Long){
        val date = Date(timestamp*1000)
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        format.timeZone = TimeZone.getTimeZone("GMT")
        _convertedTime.value = format.format(date)
    }

    fun backToUnix(readableTime: String){
        try {
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            format.timeZone = TimeZone.getTimeZone("GMT")

            val date = format.parse(readableTime)

            if(date != null){
                val timestamp = date.time/1000
                _originalTime.value = timestamp
                Log.d("ClockViewModel", "Converted timestamp: $timestamp")
            } else {
                Log.e("ClockViewModel", "Invalid date format")
            }
        } catch (e: Exception){
            Log.e("ClockViewModel", "Error parsing date: ${e.message}")
        }
    }

}