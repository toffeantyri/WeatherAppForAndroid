package ru.weather.weathertoday.busines.model

data class WeatherDataModel(
    val current: Current,
    val daily: List<DailyWeatherModel>,
    val hourly: List<HourlyWeatherModel>,
    val lat: Int,
    val lon: Int,
    val timezone: String,
    val timezone_offset: Int
)