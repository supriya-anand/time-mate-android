package com.wdf.view_model


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class ClockViewModel: ViewModel() {

    private val _convertedTime = MutableLiveData<String>()
    val convertedTime: LiveData<String> = _convertedTime

    fun convertTime(timestamp: Long){
        Log.d("ClockViewModel", "_______DATE________: $timestamp")
        val date = Date(timestamp*1000)
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        format.timeZone = TimeZone.getTimeZone("GMT")
        _convertedTime.value = format.format(date)
    }

//    fun backToUnix(timestamp: Long){
//        Log.d("ClockViewModel", "_______DATE________: $timestamp")
//        val date = Date(timestamp*1000)
//        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
//        format.timeZone = TimeZone.getTimeZone("GMT")
//        _convertedTime.value = format.format(date)
//    }

}