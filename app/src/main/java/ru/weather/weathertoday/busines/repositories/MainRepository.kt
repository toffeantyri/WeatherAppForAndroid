package ru.weather.weathertoday.busines.repositories

import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.weather.weathertoday.busines.ApiProvider
import ru.weather.weathertoday.busines.model.WeatherDataModel


const val TAG = "MAINREPO"

class MainRepository(api: ApiProvider) : BaseRepository<MainRepository.ServerResponse>(api) {

    fun reloadData(lat: String, lon: String) {
        //zip обьединяет 2 запроса, выполняет паралельно, в результат уходит в лямбду где:
        // weatherData первый запрос? geoCoding второй запрос лямбда на выходе выдает нам :
        // класс ServerResponse с данными из обоих запросов! ВАУ!
        Observable.zip(
            api.provideWeatherApi().getWeatherForecast(lat, lon),
            api.provideGeoCodingApi().getCityByCoord(lat, lon).map {
                it.asSequence() //sequance последовательноть обьекты по одному это как forEach
                    .map { model -> model.name }
                    .toList()
                    .filterNotNull()
                    .first()
            },
            { weatherData, geoCoding -> ServerResponse(geoCoding, weatherData) }
        )
            .subscribeOn(Schedulers.io()) //запускуается в BackGround потоке
            .doOnNext{ /* todo тут будет добавление обьекта в БД*/} // сделает это или next
           /* .onErrorResumeNext { todo извлечение из базы данных }*/ // при ошибке возьмет данные из БД и добавит ошибку в SAerverResponce
            .observeOn(AndroidSchedulers.mainThread()) // вернёт данные в MainThread
            .subscribe({
                dataEmitter.onNext(it)
            },{
                Log.d(TAG, "reloadData: $it")
            })   //подписываем к этому Observable 2 лямбды

    }

    data class ServerResponse(val cityName: String, val weatherData: WeatherDataModel, val error: Throwable? = null) {
    }

}