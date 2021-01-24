package com.example.asteroidradarapp.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*
 *Domain level models
 *
 * */
@Parcelize
data class Asteroid(
    val id: Long,
    val codeName: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean,
    val date: String
): Parcelable


data class PictureOfDay(
    val url: String,
    val mediaType: String,
    val title: String)