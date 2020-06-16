package com.company47.ad340weather.api

import com.squareup.moshi.Json

data class Forecast(val temp: String)
data class Coordinates(val lat: Float, val lon: Float)

data class CurrentWeather(
    val name: String,
    val coord: Coordinates,
    @field:Json(name = "main") val forecast: Forecast
)