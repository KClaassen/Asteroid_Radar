package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(application: Application) : AndroidViewModel(application)  {

    private val database = getDatabase(application)
    private val asteroidsRepository = AsteroidsRepository(database)

    val asteroids = asteroidsRepository.asteroids
    val pictureOfDay = asteroidsRepository.pictureOfDay

    private val _optionSelected = MutableLiveData<OptionSelected>()
    val optionSelected: LiveData<OptionSelected>
        get() = _optionSelected

    // Internally, we use a MutableLiveData to handle navigation to the selected property
    private val _navigateToSelectedProperty = MutableLiveData<Asteroid>()

    // The external immutable LiveData for the navigation property
    val navigateToSelectedProperty: LiveData<Asteroid>
        get() = _navigateToSelectedProperty

    init {
        viewModelScope.launch {
            try {
                showOptionSelected(OptionSelected.TODAY)
                asteroidsRepository.refreshAstroids()
                asteroidsRepository.refreshPictureOfDay()
            } catch (e:Exception) {
                Log.e("MainViewModel", e.message!!)
            }
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

    //When the property is clicked, set the [_navigateToSelectedProperty] [MutableLiveData]Asteroidthat was clicked on.
    fun displayPropertyDetails(asteroid: Asteroid) {
        _navigateToSelectedProperty.value = asteroid
    }

    // After the navigation has taken place, make sure navigateToSelectedProperty is set to null
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

    // Factory for constructing AsteroidListViewModel with parameter
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