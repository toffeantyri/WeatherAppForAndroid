package ru.weather.weathertoday.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.weather.weathertoday.busines.model.GeoCodingDataModel

interface MenuView : MvpView {

    @AddToEndSingle
    fun setLoading(flag: Boolean)

    @AddToEndSingle
    fun fillPredicitonList(data: List<GeoCodingDataModel>)

    @AddToEndSingle
    fun fillFavoriteList(data: List<GeoCodingDataModel>)


}