package com.gery.cweather.api

import com.gery.cweather.data.Clouds
import com.gery.cweather.data.Coordinates
import com.gery.cweather.data.MainData
import com.gery.cweather.data.SysData
import com.gery.cweather.data.Weather
import com.gery.cweather.data.WindData

data class WeatherResponse(
    val coord: Coordinates,
    val weather: List<Weather>,
    val base: String,
    val main: MainData,
    val visibility: Long,
    val wind: WindData,
    val rain: Map<String, Double>,
    val clouds: Clouds,
    val dt: Long,
    val sys: SysData,
    val timezone: Long,
    val id: Long,
    val name: String,
    val cod: Int
)