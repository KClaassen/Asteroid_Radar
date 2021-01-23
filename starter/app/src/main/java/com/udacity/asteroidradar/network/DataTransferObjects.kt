package com.udacity.asteroidradar.network

import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.database.DatabaseAsteroid
import com.udacity.asteroidradar.domain.Asteroid


@JsonClass(generateAdapter = true)
data class NetworkAsteroid(
        val id: Long,
        val codeName: String,
        val closeApproachDate: String,
        val absoluteMagnitude: Double,
        val estimatedDiameter: Double,
        val relativeVelocity: Double,
        val distanceFromEarth: Double,
        val isPotentiallyHazardous: Boolean,
        val date: String
)

/**
 * Convert network results to domain objects
 */
fun List<NetworkAsteroid>.asDomainModel(): List<Asteroid>{
    return this.map {
        Asteroid(
                id = it.id,
                codeName = it.codeName,
                closeApproachDate =  it.closeApproachDate,
                absoluteMagnitude = it.absoluteMagnitude,
                estimatedDiameter = it.estimatedDiameter,
                relativeVelocity = it.relativeVelocity,
                distanceFromEarth = it.distanceFromEarth,
                isPotentiallyHazardous = it.isPotentiallyHazardous,
                date = it.date
        )
    }
}

//Convert data transfer objects(network objects) to database objects
fun List<NetworkAsteroid>.asDatabaseModel(): Array<DatabaseAsteroid>{
    return this.map {
        DatabaseAsteroid(
                id = it.id,
                codeName = it.codeName,
                closeApproachDate =  it.closeApproachDate,
                absoluteMagnitude = it.absoluteMagnitude,
                estimatedDiameter = it.estimatedDiameter,
                relativeVelocity = it.relativeVelocity,
                distanceFromEarth = it.distanceFromEarth,
                isPotentiallyHazardous = it.isPotentiallyHazardous,
                date = it.date
        )
    }.toTypedArray()
}
