package ru.weather.weathertoday.busines.room

import androidx.room.Database
import androidx.room.RoomDatabase

//если меняется структура таблицы - меняется версия БД
@Database(entities = [WeatherDataEntity::class], exportSchema = false, version = 1 )
abstract class OpenWeatherDataBase : RoomDatabase() {

   abstract fun getWeatherDao() : WeatherDao

}