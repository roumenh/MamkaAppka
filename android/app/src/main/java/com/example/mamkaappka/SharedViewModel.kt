package com.example.mamkaappka

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mamkaappka.network.DayPhoto
import com.example.mamkaappka.network.DaysPhotosApi
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class SharedViewModel (): ViewModel(){


    private val _dayPhoto = MutableLiveData<DayPhoto?>()
    var dayPhoto : LiveData<DayPhoto?> = _dayPhoto

    private val _allDayPhotos = MutableLiveData<List<DayPhoto>>()
    var allDayPhotos : LiveData<List<DayPhoto>> = _allDayPhotos

    private val _errorMessage = MutableLiveData<String>()
    var errorMessage : LiveData<String> = _errorMessage

    fun fetchAllDayPhotos(){
        Log.d("MAMKAPP","Getting photos")
        val date = getCurrentDateTime()
        val dateInString = date.toString("yyyy-MM-dd")

        viewModelScope.launch {
            try {
                val result = DaysPhotosApi.retrofitService.getDayPhotos(dateInString)
                _allDayPhotos.value = result

            }catch (e: Exception){
                Log.d("MAMKAPP"," Retrofit Error : ${e.message}")
            }
        }
    }


    fun fetchTodayPhoto(){
        Log.d("MAMKAPP","Getting today photo")
        // clean current photo
        _dayPhoto.value = null

        val date = getCurrentDateTime()
        val dateInString = date.toString("yyyy-MM-dd")
        viewModelScope.launch {
            try {
                Log.d("MAMKAPP", "Dateinstring = ${dateInString}")
                val result = DaysPhotosApi.retrofitService.getTodayPhoto(dateInString)
                if (result.file == "200"){

                }
                val dp =  result.text
                Log.d("MAMKAPP",dp)

                _dayPhoto.value = result

            }catch (e: Exception){
                Log.d("MAMKAPP"," Retrofit Error : ${e.message}")

                val errorMsg = "No photo for today - ${dateInString}"
                Log.d("MAMKAPP", errorMsg)
                _errorMessage.value = errorMsg
            }
        }
    }

    init {
        Log.d("MAMKAPP","Init")
        fetchTodayPhoto()
    }


    // Function to set the _dayPhoto value to the selected one
    fun setDetailDayPhoto(selectedDayPhoto: DayPhoto){
        _dayPhoto.value = selectedDayPhoto
        Log.d("MAMKAPP","New day photo selected ${dayPhoto.value!!.date}")
    }

    fun cleanTheErrorMessage(){
        _errorMessage.value = ""
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }
}