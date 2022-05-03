package ru.weather.weathertoday.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.weather.weathertoday.busined.model.DaylyWeatherModel
import ru.weather.weathertoday.busined.model.HourlyWeatherModel
import ru.weather.weathertoday.busined.model.WeatherData

interface MainView : MvpView {


    @AddToEndSingle // Стратегия moxy которая заменяет сохраненное действие если оно было
    fun displayLocation(data: String)

    @AddToEndSingle
    fun displayCurrentData(data: WeatherData)

    @AddToEndSingle
    fun displayHourlyData(data: List<HourlyWeatherModel>)

    @AddToEndSingle
    fun displayDayliData(data: List<DaylyWeatherModel>)

    @AddToEndSingle
    fun displayError(error: Throwable)

    @AddToEndSingle
    fun setLoading(flag: Boolean)
}