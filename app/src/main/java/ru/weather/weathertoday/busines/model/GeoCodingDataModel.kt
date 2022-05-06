package ru.weather.weathertoday.busines.model

data class GeoCodingDataModel(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String,
    val state: String?,
    var isFavorite : Boolean = false // todo при добавлении городов в любимые в MenuActivity
)