package com.udacity.asteroidradar.repository

import android.net.Network
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.api.getSeventhDay
import com.udacity.asteroidradar.api.getToday
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.network.AsteroidApi
import com.udacity.asteroidradar.network.NetworkAsteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

//Repository for fetching Asteroids from network and storing them on disk
class AsteroidsRepository(private val database: AsteroidDatabase) {

    var AsteroidApiService = AsteroidApi.retrofitService


    suspend fun refreshAstroids() {
        withContext(Dispatchers.IO) {
            val jsonResult = AsteroidApiService.getAsteroids(getToday(), getSeventhDay(), API_KEY)
            val asteroids = parseAsteroidsJsonResult(JSONObject(jsonResult))
            database.astreoidsDao.insertAll(*asteroids.asDatabaseModel())
        }
    }

}