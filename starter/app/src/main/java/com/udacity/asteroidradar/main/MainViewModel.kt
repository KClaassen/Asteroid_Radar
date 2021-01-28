package com.udacity.asteroidradar.main

import android.app.Application
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.*
import com.example.asteroidradarapp.domain.Asteroid
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application)  {

    private val database = getDatabase(application)
    private val asteroidsRepository = AsteroidsRepository(database)

    val asteroids = asteroidsRepository.asteroids
    val pictureOfDay = asteroidsRepository.pictureOfDay
//Added
    private val _optionSelected = MutableLiveData<OptionSelected>()
    val optionSelected: LiveData<OptionSelected>
        get() = _optionSelected

    init {
        viewModelScope.launch {
            showOptionSelected(OptionSelected.TODAY)
            asteroidsRepository.refreshAstroids()
            asteroidsRepository.refreshPictureOfDay()
        }
    }

//Added
    val asteroidList = Transformations.switchMap(_optionSelected) {
        when(it) {
            OptionSelected.WEEK -> asteroidsRepository.asteroidsWeek
            OptionSelected.TODAY -> asteroidsRepository.asteroids
            OptionSelected.SAVED -> asteroidsRepository.asteroidSaved
        }
    }

    fun showOptionSelected(optionSelected: OptionSelected) {
        _optionSelected.value = optionSelected
    }

    /**
     * Factory for constructing AsteroidListViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}