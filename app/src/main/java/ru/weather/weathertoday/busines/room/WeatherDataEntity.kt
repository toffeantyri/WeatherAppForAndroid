package ru.weather.weathertoday.busines.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//модель для Room БД
@Entity(tableName = "WeatherData")
data class WeatherDataEntity(
    @PrimaryKey val id : Int = 1,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "json_data") val data : String
)
