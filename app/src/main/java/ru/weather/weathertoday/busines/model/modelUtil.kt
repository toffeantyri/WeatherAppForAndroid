package ru.weather.weathertoday.busines.model

import ru.weather.weathertoday.busines.room.GeoCodeEntity

fun GeoCodingDataModel.toEntity(): GeoCodeEntity =
    GeoCodeEntity(
        name = this.name,
        localNames = this.local_names,
        lat = this.lat.toString(),
        lon = this.lon.toString(),
        country = this.country,
        state = this.state,
        isFavorite = this.isFavorite
    )


fun GeoCodeEntity.toDataModel(): GeoCodingDataModel =
    GeoCodingDataModel(
        country = this.country,
        lat = this.lat.toDouble(),
        lon = this.lon.toDouble(),
        local_names = this.localNames,
        state = this.state,
        name = this.name,
        isFavorite = this.isFavorite
    )

