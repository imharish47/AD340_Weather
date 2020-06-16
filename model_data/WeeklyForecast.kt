package com.company47.ad340weather.model_data

import com.squareup.moshi.Json

data class WeatherDescription(val main:String,
val description: String,
val icon: String)


data class Temp(val max:Float,val min:Float)

data class  DailyForecast(
    @field:Json(name="dt") val date:Long,
    val temp: Temp,
    val weather:List<WeatherDescription>
)

data class WeeklyForecast(val daily:List<DailyForecast>)