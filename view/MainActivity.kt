package com.company47.ad340weather.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company47.ad340weather.R
import com.company47.ad340weather.`interface`.AppNavigator
import com.company47.ad340weather.adapter.DailyForecastAdapter
import com.company47.ad340weather.databinding.ActivityMainBinding
import com.company47.ad340weather.location.CurrentForecastFragment
import com.company47.ad340weather.location.LocationEntryFragment
import com.company47.ad340weather.model_data.DailyForecast
import com.company47.ad340weather.repository.ForecastRepository
import com.company47.ad340weather.utils.TempDisplaySettingManager
import com.company47.ad340weather.utils.disAlertDialog

class MainActivity : AppCompatActivity(),AppNavigator {

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager;
    private lateinit var binding: ActivityMainBinding
  //  private val forecastRepository = ForecastRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        val view: View = binding.root
        setContentView(view)
        tempDisplaySettingManager = TempDisplaySettingManager(this)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer,LocationEntryFragment())
            .commit()

    }

    //region MENU
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.tempDisplaySettings -> {
                disAlertDialog(this, tempDisplaySettingManager)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
    // endregion
    override fun navigateToCurrentForecast(zipcode: String) {
        //forecastRepository.loadForecastData(zipcode)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer,CurrentForecastFragment.newInstance(zipcode))
            .commit()
    }

    override fun navigateToLocationEntry() {
supportFragmentManager.beginTransaction()
    .replace(R.id.fragmentContainer,LocationEntryFragment())
    .commit()
    }
}