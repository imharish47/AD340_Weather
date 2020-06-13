package com.company47.ad340weather.location

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company47.ad340weather.R
import com.company47.ad340weather.`interface`.AppNavigator
import com.company47.ad340weather.adapter.DailyForecastAdapter
import com.company47.ad340weather.model_data.DailyForecast
import com.company47.ad340weather.repository.ForecastRepository
import com.company47.ad340weather.utils.TempDisplaySettingManager
import com.company47.ad340weather.view.ForecastDetailsActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class CurrentForecastFragment : Fragment() {
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager;
    private val forecastRepository = ForecastRepository()

    private lateinit var appNavigator: AppNavigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appNavigator = context as AppNavigator
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val zipCode = arguments!!.getString(KEY_ZIP_CODE) ?: ""
        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        val view = inflater.inflate(R.layout.fragment_current_forecast, container, false)

        val locationEntryBtn: FloatingActionButton = view.findViewById(R.id.floatingActionButton)
        locationEntryBtn.setOnClickListener {
            appNavigator.navigateToLocationEntry()
        }
        // region RecyclerView Initialisation

        val forecastList: RecyclerView = view.findViewById(R.id.recyclerView)
        forecastList.layoutManager = LinearLayoutManager(requireContext())
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

        forecastRepository.loadForecastData(zipCode)
        return view
    }

    // region Intent-> MainActivity-->ForecastDetailsActivity
    private fun showForecastDetails(forecastItem: DailyForecast) {
        val forecastDetailsActIntent = Intent(requireContext(), ForecastDetailsActivity::class.java)
        forecastDetailsActIntent.putExtra("key_temp", forecastItem.temp)
        forecastDetailsActIntent.putExtra("key_desc", forecastItem.description)
        startActivity(forecastDetailsActIntent)
    }
    // endregion

    //region MainActivity To CurrentForecastFragment DATA passing through fragment->Args
    companion object {
        const val KEY_ZIP_CODE = "key_zipcode"
        fun newInstance(zipCode: String): CurrentForecastFragment {
            val fragment = CurrentForecastFragment()
            val args = Bundle()
            args.putString(KEY_ZIP_CODE, zipCode)
            fragment.arguments = args
            return fragment
        }
    }
    //endregion
}