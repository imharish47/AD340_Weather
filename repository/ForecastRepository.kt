package com.company47.ad340weather.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.company47.ad340weather.model_data.DailyForecast
import kotlin.random.Random

class ForecastRepository {
    private  val TAG = "ForecastRepository"

    private val _weeklyForecast = MutableLiveData<List<DailyForecast>>()
    val weeklyForecast: LiveData<List<DailyForecast>> = _weeklyForecast

    /**
     * Loads Data from Data source to be provided for Views
     */

    fun loadForecastData(zipCode: String) {
        val randomValues = List(7) { Random.nextFloat().rem(100) * 100 }
        Log.d(TAG, "loadForecast: "+randomValues.toString())

        val forecastItems = randomValues.map { temp ->
            DailyForecast(
                temp,
                description = getTempDescription(temp)
            )
        }

        _weeklyForecast.value = forecastItems
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