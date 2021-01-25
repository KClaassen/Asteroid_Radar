package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AsteroidsDao {

    //Loads all asteroids from databaseasteroid and returns them as a List
    @Query("SELECT  * FROM databaseasteroid")
    fun getAsteroids() : LiveData<List<DatabaseAsteroid>>

    //Store values in cache
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg asteroids: DatabaseAsteroid)
}

@Dao
interface PictureOfDayDao {

    @Query("SELECT * FROM databasepictureofday")
    fun getPictureOfDay(): LiveData<DatabasePictureOfDay>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPictureOfDay(pictureOfDay: DatabasePictureOfDay)
}


@Database(entities = [DatabaseAsteroid::class], version = 1, exportSchema = false)
abstract class AsteroidDatabase: RoomDatabase() {
    abstract val astreoidsDao: AsteroidsDao
}

private lateinit var INSTANCE: AsteroidDatabase

fun getDatabase(context: Context): AsteroidDatabase{
    synchronized(AsteroidDatabase::class.java){
        var instance = INSTANCE

        if(instance == null){
            instance = Room.databaseBuilder(
                    context.applicationContext,
                    AsteroidDatabase::class.java,
                    "asteroids")
                    .fallbackToDestructiveMigration()
                    .build()
            INSTANCE =instance
        }

        return instance
    }
}