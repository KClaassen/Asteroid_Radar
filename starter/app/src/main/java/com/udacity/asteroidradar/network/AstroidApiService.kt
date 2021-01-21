package com.udacity.asteroidradar.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.nasa.gov"

private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()



interface AstroidApiService {

    @GET("neo/rest/v1/feed")
    fun getProperties(
            @Query("start_date") startDate: String,
            @Query("end_date") endDate: String,
            @Query("api_key") apiKey: String) : String

}

object AstroidApi {
    val retrofitService : AstroidApiService by lazy {
        retrofit.create(AstroidApiService::class.java)
    }
}