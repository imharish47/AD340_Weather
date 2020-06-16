package com.company47.ad340weather.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.company47.ad340weather.api.CurrentWeather
import com.company47.ad340weather.api.createOpenWeatherMapService
import com.company47.ad340weather.model_data.WeeklyForecast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val OPEN_WEATHER_MAP_API_KEY = "e3984ab1d6d74e29822a4ea7b789348a"

class ForecastRepository {
    private val TAG = "ForecastRepository"

    private val _currentWeather = MutableLiveData<CurrentWeather>()
    val currentWeather: LiveData<CurrentWeather> = _currentWeather

    private val _weeklyForecast = MutableLiveData<WeeklyForecast>()
    val weeklyForecast: LiveData<WeeklyForecast> = _weeklyForecast

    /**
     * Loads Data from Data source to be provided for Views
     */

    fun loadCurrentForecast(zipCode: String) {
        val call = createOpenWeatherMapService().currentWeather(
            zipCode,
            "imperial",
            OPEN_WEATHER_MAP_API_KEY
        )
        call.enqueue(object : Callback<CurrentWeather> {
            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e(ForecastRepository::class.java.simpleName, "Error loading current weather", t)
            }

            override fun onResponse(
                call: Call<CurrentWeather>,
                response: Response<CurrentWeather>
            ) {
                val weatherResponse = response.body()
                if (weatherResponse != null) {
                  _currentWeather.value = weatherResponse
                }
            }

        })

    }


    fun loadWeeklyForecast(zipCode: String) {
        val call = createOpenWeatherMapService().currentWeather(
            zipCode,
            "imperial",
            OPEN_WEATHER_MAP_API_KEY
        )
        call.enqueue(object : Callback<CurrentWeather> {
            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e(
                    ForecastRepository::class.java.simpleName,
                    "Error loading location for weekly forecast",
                    t
                )
            }

            override fun onResponse(
                call: Call<CurrentWeather>,
                response: Response<CurrentWeather>
            ) {
                val weatherResponse = response.body()
                if (weatherResponse != null) {
                    //load 7 day forecast
                    val forecastCall = createOpenWeatherMapService().sevenDayForecast(
                        lat = weatherResponse.coord.lat,
                        lon = weatherResponse.coord.lon,
                        exclude = "current,minutely,hourly",
                        units = "imperial",
                        apiKey = OPEN_WEATHER_MAP_API_KEY
                    )

                    forecastCall.enqueue(object : Callback<WeeklyForecast> {
                        override fun onFailure(call: Call<WeeklyForecast>, t: Throwable) {
                            Log.e(
                                ForecastRepository::class.java.simpleName,
                                "Error loading  weekly forecast",
                                t
                            )
                        }

                        override fun onResponse(
                            call: Call<WeeklyForecast>,
                            response: Response<WeeklyForecast>
                        ) {
                            val weeklyForecastResponse = response.body()

                            if (weeklyForecastResponse != null) {
                                _weeklyForecast.value = weeklyForecastResponse
                            }
                        }

                    })
                }
            }

        })
    }

    private fun getTempDescription(temp: Float): String {
        return when (temp) {
            in Float.MIN_VALUE.rangeTo(0f) -> "Anything below 0 does'nt make sense"
            in 0f.rangeTo(32f) -> "Way too cold"
            in 32f.rangeTo(55f) -> "Colder than i would prefer"
            in 55f.rangeTo(65f) -> "Getting better"
            in 65f.rangeTo(80f) -> "That's the sweet spot!"
            in 80f.rangeTo(90f) -> "Getting a little warm"
            in 90f.rangeTo(100f) -> "Where's the A/C?"
            in 100f.rangeTo(Float.MAX_VALUE) -> "What is this, Arizona?"
            else -> "Does not compute"
        }
    }

}