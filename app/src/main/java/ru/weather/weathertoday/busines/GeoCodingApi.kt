package ru.weather.weathertoday.busines

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import ru.weather.weathertoday.busines.model.GeoCodingDataModel

interface GeoCodingApi {

    @GET("geo/1.0/reverse?")
    fun getCityByCoord(
        @Query("lat") lat : String,
        @Query("lon") lon : String,
        @Query("limit") limit : String = "10",
        @Query("appid") appid : String = "d06982b041a769656da1d71dc9d4d8c0"
    ) : Observable<List<GeoCodingDataModel>>

}