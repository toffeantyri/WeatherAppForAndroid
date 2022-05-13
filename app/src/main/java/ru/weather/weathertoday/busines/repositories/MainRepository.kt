package ru.weather.weathertoday.busines.repositories

import android.util.Log
import com.google.gson.Gson
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.weather.weathertoday.busines.ApiProvider
import ru.weather.weathertoday.busines.model.WeatherDataModel
import ru.weather.weathertoday.busines.room.WeatherDataEntity


const val TAG = "MAINREPO"

class MainRepository(api: ApiProvider) : BaseRepository<MainRepository.ServerResponse>(api) {

    private val gson = Gson()
    private val dbAccess = db.getWeatherDao()


    fun reloadData(lat: String, lon: String) {
        //zip обьединяет 2 запроса, выполняет паралельно, в результат уходит в лямбду где:
        // weatherData первый запрос? geoCoding второй запрос -> лямбда на выходе выдает нам :
        // класс ServerResponse с данными из обоих запросов! ВАУ!
        Observable.zip(
            api.provideWeatherApi().getWeatherForecast(lat, lon),
            api.provideGeoCodingApi().getCityByCoord(lat, lon).map {
                it.asSequence() //sequance последовательноть обьекты по одному это как forEach
                    .map { model -> model.name }
                    .toList()
                    //todo настройть локализацию проекта
                    .filterNotNull()
                    .first()
            },
            { weatherData, geoCoding -> ServerResponse(geoCoding, weatherData) }
        )
            .subscribeOn(Schedulers.io()) //запускуается в BackGround потоке
            .doOnNext {
                dbAccess.insertWeatherData(WeatherDataEntity(data = gson.toJson(it.weatherData), city = it.cityName))
            } // сделает это или next
            .onErrorResumeNext {
                Observable.just(
                    ServerResponse(
                        dbAccess.getWeatherData().city,
                        gson.fromJson(dbAccess.getWeatherData().data, WeatherDataModel::class.java),
                        it
                    )
                )
            } // при ошибке возьмет данные из БД и добавит ошибку в ServerResponse
            .observeOn(AndroidSchedulers.mainThread()) // вернёт данные в MainThread
            .subscribe({
                dataEmitter.onNext(it)
            }, {
                Log.d(TAG, "reloadData: $it")
            })   //подписываем к этому Observable 2 лямбды

    }

    data class ServerResponse(val cityName: String, val weatherData: WeatherDataModel, val error: Throwable? = null) {
    }

}