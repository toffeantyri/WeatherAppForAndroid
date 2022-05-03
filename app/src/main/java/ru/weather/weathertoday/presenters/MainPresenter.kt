package ru.weather.weathertoday.presenters

import ru.weather.weathertoday.view.MainView


class MainPresenter : BasePresenter<MainView>() {

    //todo тут будут переменные репозитория


    override fun enable() {

    }

    fun refresh(lat: String, lon: String){
        viewState.setLoading(true)

        //todo обращение к репозиторию передадим наши координаты

    }
}