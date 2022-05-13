package ru.weather.weathertoday.busines.repositories

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import ru.weather.weathertoday.App
import ru.weather.weathertoday.busines.ApiProvider

abstract class BaseRepository<T>(val api: ApiProvider) {

    //обьект Observer и Observable подписываемся на него в mainRepo enable
    val dataEmitter: BehaviorSubject<T> = BehaviorSubject.create()
    //если надо разное поведение в зависимости откуда получен ответ тогда много BehaviorSubj сделать

    protected val db by lazy { App.db }


    //асинх обращение к  ДБ
    protected fun roomTransaction(
        transaction: () -> T
    ) = Observable.fromCallable { transaction() } //from callable преобразует вызываемый в наблюдаемый (результат функции )
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { dataEmitter.onNext(it) }


}