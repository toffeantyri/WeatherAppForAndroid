package ru.weather.weathertoday.busines

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/onecall?")
    fun getWeatherForecast(
        @Query("lat") lat : String,
        @Query("lon") lon : String,
        @Query("exclude") excluse : String = "minutely,alerts",
        @Query("appid") appid : String = "d06982b041a769656da1d71dc9d4d8c0",
        @Query("lang") lang : String = "en",
    ) // todo в rxJava полученное
}