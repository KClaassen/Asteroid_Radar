package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.domain.Asteroid

//Creates the DatabaseAsteroid Database Object
@Entity
data class DatabaseAsteroid constructor(
        @PrimaryKey
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

// Converts Database Objects to Domain Objects
fun List<DatabaseAsteroid>.asDomainModel(): List<Asteroid>{
    return map{
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