package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AsteroidsDao {

    //Loads all asteroids from databaseasteroid and returns them as a List
    @Query("SELECT  * FROM databaseasteroid ORDER BY closeApproachDate DESC")
    fun getAsteroids() : LiveData<List<DatabaseAsteroid>>

    @Query("SELECT * FROM DatabaseAsteroid WHERE closeApproachDate=:today ORDER BY closeApproachDate ASC")
    fun getAsteroidsToday(today: String): LiveData<List<DatabaseAsteroid>>

    @Query("SELECT * FROM DatabaseAsteroid WHERE closeApproachDate BETWEEN :today AND :seventhDay ORDER BY closeApproachDate ASC")
    fun getAsteroidsWeek(today: String, seventhDay: String): LiveData<List<DatabaseAsteroid>>

    //Store values in cache
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg asteroids: DatabaseAsteroid)
}

@Dao
interface PictureOfDayDao {

    @Query("SELECT * FROM databasepictureofday")
    fun getPictureOfDay(): LiveData<DatabasePictureOfDay>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPicture(pictureOfDay: DatabasePictureOfDay)
}


@Database(entities = [DatabaseAsteroid::class, DatabasePictureOfDay::class], version = 1, exportSchema = false)
abstract class AsteroidDatabase: RoomDatabase() {
    abstract val asteroidsDao: AsteroidsDao
    abstract val pictureOfDayDao: PictureOfDayDao
}



private lateinit var INSTANCE: AsteroidDatabase

fun getDatabase(context: Context): AsteroidDatabase {
    synchronized(AsteroidDatabase::class.java) {
        if(!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                    AsteroidDatabase::class.java,
                    "asteroids")
                    .fallbackToDestructiveMigration()
                    .build()
        }
    }
    return INSTANCE
}