package ru.weather.weathertoday.presenters

import android.util.Log
import ru.weather.weathertoday.busines.ApiProvider
import ru.weather.weathertoday.busines.repositories.MainRepository
import ru.weather.weathertoday.busines.repositories.TAG
import ru.weather.weathertoday.view.MainView


class MainPresenter : BasePresenter<MainView>() {

    private val repo = MainRepository(ApiProvider())

    override fun enable() {
        repo.dataEmitter.subscribe { response ->
            Log.d(TAG, "Presenter.enable() : $response")
            viewState.displayLocation(response.cityName)
            viewState.displayCurrentData(response.weatherData)
            viewState.displayDaylyData(response.weatherData.daily)
            viewState.displayHourlyData(response.weatherData.hourly)
            response.error?.let { viewState.displayError(response.error) }
        }
    }

    fun refresh(lat: String, lon: String) {
        viewState.setLoading(true)
        repo.reloadData(lat, lon)
    }
}