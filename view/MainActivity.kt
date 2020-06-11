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
import com.company47.ad340weather.adapter.DailyForecastAdapter
import com.company47.ad340weather.databinding.ActivityMainBinding
import com.company47.ad340weather.model_data.DailyForecast
import com.company47.ad340weather.repository.ForecastRepository
import com.company47.ad340weather.utils.TempDisplaySettingManager
import com.company47.ad340weather.utils.disAlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager;
    private lateinit var binding: ActivityMainBinding
    private val forecastRepository = ForecastRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        val view: View = binding.root
        setContentView(view)
        tempDisplaySettingManager = TempDisplaySettingManager(this)

        //region Submit Button OnClick
        binding.submitBtn.setOnClickListener {
            val zipCode: String = binding.zipcodeET.text.toString()

            if (zipCode.length != 5) {
                Toast.makeText(this, "please enter valid zipCode", Toast.LENGTH_SHORT).show()
            } else {
                forecastRepository.loadForecastData(zipCode)
            }
        }
        // endregion

        // region RecyclerView Initialisation
        val forecastList: RecyclerView = binding.recyclerView
        forecastList.layoutManager = LinearLayoutManager(this)
        val dailyForecastAdapter =
            DailyForecastAdapter(tempDisplaySettingManager) { forecastItem ->

                showForecastDetails(forecastItem)

            }
        forecastList.adapter = dailyForecastAdapter

        //endregion

        // region LiveData Observer
        val weeklyForecastObserver = Observer<List<DailyForecast>> { forecastItem ->
            //update our list adapter
            dailyForecastAdapter.submitList(forecastItem)
        }

        forecastRepository.weeklyForecast.observe(this, weeklyForecastObserver)
        //endregion

    }

    // region Intent-> MainActivity-->ForecastDetailsActivity
    private fun showForecastDetails(forecastItem: DailyForecast) {
        val forecastDetailsActIntent = Intent(this, ForecastDetailsActivity::class.java)
        forecastDetailsActIntent.putExtra("key_temp", forecastItem.temp)
        forecastDetailsActIntent.putExtra("key_desc", forecastItem.description)
        startActivity(forecastDetailsActIntent)
    }
    // endregion




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
}