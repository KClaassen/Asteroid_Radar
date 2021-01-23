package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.network.AsteroidApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

//    init {
//        getAsteroidProperties()
//    }

//    private fun getAsteroidProperties() {
//        AsteroidApi.retrofitService.getAsteroids().enqueue(
//                object: Callback<String> {
//                    override fun onFailure(call: Call<String>, t: Throwable) {
//                        _status.value = "Failure: " + t.message
//                    }
//
//                    override fun onResponse(call: Call<String>, response: Response<String>) {
//                        _status.value = response.body()
//                    }
//                })
//    }
}