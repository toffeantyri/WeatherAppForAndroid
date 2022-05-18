package ru.weather.weathertoday.busines.room

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import ru.weather.weathertoday.busines.model.LocalNames

@Entity(tableName = "GeoCode", primaryKeys = ["lat", "lon"])
data class GeoCodeEntity(
    @ColumnInfo(name = "name") val name : String,
    @Embedded val localNames : LocalNames,
    @ColumnInfo(name = "lat") val lat : String,
    @ColumnInfo(name = "lon") val lon : String,
    @ColumnInfo(name = "country") val country : String,
    @ColumnInfo(name = "state") val state : String?,
    @ColumnInfo(name = "isFavorite") val isFavorite : Boolean


)