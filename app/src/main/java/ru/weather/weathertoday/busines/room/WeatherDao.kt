package ru.weather.weathertoday.busines.room

import androidx.room.*

//Data Access Object
@Dao
interface WeatherDao {

    @Query("SELECT * FROM WeatherData WHERE id = 1")
    fun getWeatherData() : WeatherDataEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE) // при конфликте (когда id совпадают, будет замена данных)
    fun insertWeatherData(data: WeatherDataEntity)

    @Update
    fun updateWeatherData(data: WeatherDataEntity)

    @Delete
    fun deleteWeatherData(data: WeatherDataEntity)




}