package ru.weather.weathertoday.presenters

import android.util.Log
import ru.weather.weathertoday.activity.TAG
import ru.weather.weathertoday.busines.ApiProvider
import ru.weather.weathertoday.busines.model.GeoCodingDataModel
import ru.weather.weathertoday.busines.repositories.MenuRepository
import ru.weather.weathertoday.busines.repositories.SAVED
import ru.weather.weathertoday.view.MenuView


class MenuPresenter : BasePresenter<MenuView>() {

    private val repo = MenuRepository(ApiProvider())

    override fun enable() {
        repo.dataEmitter.subscribe {
            viewState.setLoading(false)
            if (it.purpose == SAVED) {
                Log.d(TAG, "enable SAVED : ${it.data}")
                viewState.fillFavoriteList(it.data)
            } else {
                Log.d(TAG, "enable CURRENT : ${it.data}")
                viewState.fillPredictionList(it.data)
            }

        }
    }

    fun getFavoriteList() {
        repo.updateFavorite()
    }

    fun searchFor(cityName: String) {
        repo.getCities(cityName)
    }

    fun removeLocation(item: GeoCodingDataModel) {
        repo.remove(item)
    }

    fun saveLocation(item: GeoCodingDataModel) {
        repo.add(item)
    }


}