package com.udacity.asteroidradar.network

import com.example.asteroidradarapp.domain.PictureOfDay
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.database.DatabaseAsteroid
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.DatabasePictureOfDay

/**
 * Asteroid
 */

@JsonClass(generateAdapter = true)
data class NetworkAsteroid(
        val id: Long,
        val codeName: String,
        val closeApproachDate: String,
        val absoluteMagnitude: Double,
        val estimatedDiameter: Double,
        val relativeVelocity: Double,
        val distanceFromEarth: Double,
        val isPotentiallyHazardous: Boolean
)

//Convert network results to domain objects

fun List<NetworkAsteroid>.asDomainModel(): List<Asteroid>{
    return this.map {
        Asteroid(
                id = it.id,
                codename = it.codeName,
                closeApproachDate =  it.closeApproachDate,
                absoluteMagnitude = it.absoluteMagnitude,
                estimatedDiameter = it.estimatedDiameter,
                relativeVelocity = it.relativeVelocity,
                distanceFromEarth = it.distanceFromEarth,
                isPotentiallyHazardous = it.isPotentiallyHazardous
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
                isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }.toTypedArray()
}

/**
 * Picture of Day
 */

data class NetworkPictureOfDay(
        val url: String,
        @Json(name = "media_type") val mediaType: String,
        val title: String
)


//Convert network results to domain objects

fun NetworkPictureOfDay.asDomainModel(): PictureOfDay {
    return PictureOfDay(
            url = this.url,
            mediaType = this.mediaType,
            title = this.title
    )

}


//Convert data transfer objects(network objects) to database objects

fun NetworkPictureOfDay.asDatabaseModel(): DatabasePictureOfDay {
    return DatabasePictureOfDay(
            url = this.url,
            mediaType = this.mediaType,
            title = this.title
    )

}
