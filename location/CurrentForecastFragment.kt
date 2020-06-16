package com.company47.ad340weather.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company47.ad340weather.Location
import com.company47.ad340weather.LocationRepository
import com.company47.ad340weather.R
import com.company47.ad340weather.adapter.DailyForecastAdapter
import com.company47.ad340weather.api.CurrentWeather
import com.company47.ad340weather.model_data.DailyForecast
import com.company47.ad340weather.repository.ForecastRepository
import com.company47.ad340weather.utils.TempDisplaySettingManager
import com.google.android.material.floatingactionbutton.FloatingActionButton


class CurrentForecastFragment : Fragment() {
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager;
    private val forecastRepository = ForecastRepository()
private lateinit var locationRepository: LocationRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_current_forecast, container, false)

        val locationName:TextView=view.findViewById(R.id.locationName)
        val locationTemp:TextView=view.findViewById(R.id.locationTemp)

        val zipCode = arguments?.getString(KEY_ZIP_CODE) ?: ""
        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        //region  CurrentForecastFrag -> LocationEntryFrag
        val locationEntryBtn: FloatingActionButton = view.findViewById(R.id.floatingActionButton)
        locationEntryBtn.setOnClickListener {
            // CurrentForecastFrag -> LocationEntryFrag
            showLocationEntry()
        }
        //endregion




        // region LiveData Observer
        val currentWeatherObserver = Observer<CurrentWeather> { weather ->
            //update our list adapter
          locationName.text=weather.name
            locationTemp.text=weather.forecast.temp
        }

        forecastRepository.currentWeather.observe(requireActivity(), currentWeatherObserver)
        //endregion

        locationRepository= LocationRepository(requireContext())
        val savedLocationObserver=Observer<Location>{savedLocation->
            when(savedLocation){
                is Location.Zipcode->forecastRepository.loadCurrentForecast(savedLocation.zipcode)

            }
        }
        locationRepository.savedLocation.observe(viewLifecycleOwner,savedLocationObserver)

        return view
    }


    //region CurrentForecastFrag -> LocationEntryFrag
    private fun showLocationEntry() {
        val action =
            CurrentForecastFragmentDirections.actionCurrentForecastFragmentToLocationEntryFragment()
        findNavController().navigate(action)
    }
    //endregion



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