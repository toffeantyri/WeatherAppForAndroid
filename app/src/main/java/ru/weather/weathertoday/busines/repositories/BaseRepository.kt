package ru.weather.weathertoday.busines.repositories

import io.reactivex.rxjava3.subjects.BehaviorSubject
import ru.weather.weathertoday.busines.ApiProvider

abstract class BaseRepository<T>(val api : ApiProvider) {

    //обьект Observer и Observable подписываемся на него в mainRepo enable
    val dataEmitter: BehaviorSubject<T> = BehaviorSubject.create()
    //если надо разное поведение в зависимости откуда получен ответ тогда много BehaviorSubj сделать



}