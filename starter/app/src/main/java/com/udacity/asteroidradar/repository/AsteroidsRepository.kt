package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.network.AsteroidApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

//Repository for fetching Asteroids from network and storing them on disk
class AsteroidsRepository(private val database: AsteroidDatabase) {

    var AsteroidApiService = AsteroidApi.retrofitService

    suspend fun refreshAstroids() {
        withContext(Dispatchers.IO) {
            val jsonResult = AsteroidApiService.getAsteroids()
            val asteroids = parseAsteroidsJsonResult(JSONObject(jsonResult))
        }
    }

}