package com.company47.ad340weather.details

import java.util.*

data class ForecastDetailsViewState(
    val temp:Float,
    val description:String,
    val date: String,
    val iconUrl:String
)