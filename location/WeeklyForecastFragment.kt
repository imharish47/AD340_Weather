package com.company47.ad340weather.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company47.ad340weather.R
import com.company47.ad340weather.adapter.DailyForecastAdapter
import com.company47.ad340weather.model_data.DailyForecast
import com.company47.ad340weather.repository.ForecastRepository
import com.company47.ad340weather.utils.TempDisplaySettingManager
import com.google.android.material.floatingactionbutton.FloatingActionButton


class WeeklyForecastFragment : Fragment() {
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager;
    private val forecastRepository = ForecastRepository()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val view = inflater.inflate(R.layout.fragment_weekly_forecast, container, false)
        val zipCode = arguments?.getString(KEY_ZIP_CODE) ?: ""
        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())
        //region Interface CurrentForecastFrag -> LocationEntryFrag
        val locationEntryBtn: FloatingActionButton = view.findViewById(R.id.floatingActionButton)
        locationEntryBtn.setOnClickListener {
            // CurrentForecastFrag -> LocationEntryFrag
            showLocationEntry()
        }
        //endregion


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

        forecastRepository.weeklyForecast.observe(requireActivity(), weeklyForecastObserver)
        //endregion

        //loading data from repository
        forecastRepository.loadForecastData(zipCode)
        return view
    }

    private fun showLocationEntry() {
        val action =
            WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToLocationEntryFragment()
        findNavController().navigate(action)
    }

    // region Intent-> CurrentForecastFrag-->ForecastDetailsActivity
    private fun showForecastDetails(forecastItem: DailyForecast) {
        val action =
            WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToForecastDetailFragment(
                forecastItem.temp, forecastItem.description
            )
        findNavController().navigate(action)
    }
    // endregion

    //region MainActivity To CurrentForecastFragment DATA passing through fragment->Args
    companion object {
        const val KEY_ZIP_CODE = "key_zipcode"
        fun newInstance(zipCode: String): WeeklyForecastFragment {
            val fragment = WeeklyForecastFragment()
            val args = Bundle()
            args.putString(KEY_ZIP_CODE, zipCode)
            fragment.arguments = args
            return fragment
        }
    }
    //endregion
}