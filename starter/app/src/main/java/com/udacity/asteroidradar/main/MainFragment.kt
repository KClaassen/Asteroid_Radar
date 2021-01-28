package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.adapter.AsteroidsAdapter
import com.udacity.asteroidradar.databinding.FragmentMainBinding

enum class OptionSelected { TODAY, WEEK, SAVED }

class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding

    /**
     * Lazily initialize our [AsteroidListViewModel].
     */
    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity) {
        }
        ViewModelProvider(this, MainViewModel.Factory(activity.application)).get(
            MainViewModel::class.java
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

//added
        binding.asteroidRecycler.adapter = AsteroidsAdapter(AsteroidsAdapter.OnClickListener {
            viewModel.showOptionSelected(OptionSelected.WEEK)
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

//Added
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.show_week_asteroids -> {
                viewModel.showOptionSelected(OptionSelected.WEEK)
            }
            R.id.show_today_asteroids -> {
                viewModel.showOptionSelected(OptionSelected.TODAY)
            }
            R.id.show_saved_asteroids -> {
                viewModel.showOptionSelected(OptionSelected.SAVED)
            }
        }
        return true
    }
}


