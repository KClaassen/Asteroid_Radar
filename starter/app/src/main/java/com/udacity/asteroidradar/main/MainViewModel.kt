package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.network.AstroidApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    init {
        getAstroidProperties()
    }

    private fun getAstroidProperties() {
        AstroidApi.retrofitService.getProperties().enqueue(
                object: Callback<Asteroid> {
                    override fun onFailure(call: Call<Asteroid>, t: Throwable) {
                        _status.value = "Failure: " + t.message
                    }

                    override fun onResponse(call: Call<Asteroid>, response: Response<Asteroid>) {
                        _status.value = "Success: ${response.body()} Astroids received"
                    }
                })
    }
}