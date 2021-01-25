package com.udacity.asteroidradar.repository

import android.net.Network
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.asteroidradarapp.domain.PictureOfDay
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.api.getSeventhDay
import com.udacity.asteroidradar.api.getToday
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.DatabasePictureOfDay
import com.udacity.asteroidradar.network.AsteroidApi
import com.udacity.asteroidradar.network.NetworkAsteroid
import com.udacity.asteroidradar.network.NetworkPictureOfDay
import com.udacity.asteroidradar.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

//Repository for fetching Asteroids from network and storing them on disk
class AsteroidsRepository(private val database: AsteroidDatabase) {

    var AsteroidApiService = AsteroidApi.retrofitService

    /**
     * Asteroidlist that can be shown on the screen.
     */
    val asteroids: LiveData<List<Asteroid>> = Transformations.map(database.astreoidsDao.getAsteroids()) {
        it.asDomainModel()
    }

    val pictureOfDay: LiveData<PictureOfDay> = Transformations.map(database.)


    suspend fun refreshAstroids() {
        withContext(Dispatchers.IO) {
            val jsonResult = AsteroidApiService.getAsteroids(getToday(), getSeventhDay(), API_KEY)
            val asteroids = parseAsteroidsJsonResult(JSONObject(jsonResult))
            val networkAsteroidList = asteroids.map {
                NetworkAsteroid(
                        it.id,
                        it.codename,
                        it.closeApproachDate,
                        it.absoluteMagnitude,
                        it.estimatedDiameter,
                        it.relativeVelocity,
                        it.distanceFromEarth,
                        it.isPotentiallyHazardous)
            }
            database.astreoidsDao.insertAll(*networkAsteroidList.asDatabaseModel())
        }
    }

    suspend fun refreshPictureOfDay() {
        withContext(Dispatchers.IO) {
            val jsonResult = AsteroidApiService.getPictureOfDay(API_KEY)
            val pictureOfDay = jsonResult.asDatabaseModel()
            database.
        }
    }
}