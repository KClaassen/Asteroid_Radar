package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidsRepository
import retrofit2.HttpException

class RefreshDataWork(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val database =  getDatabase(applicationContext)
        val repository = AsteroidsRepository(database)

        return try {
            repository.refreshAstroids()
            Result.success()
        }catch (exception: HttpException) {
            Result.retry()
        }
    }
}