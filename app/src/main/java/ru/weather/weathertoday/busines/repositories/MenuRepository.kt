package ru.weather.weathertoday.busines.repositories

import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.weather.weathertoday.activity.TAG
import ru.weather.weathertoday.busines.ApiProvider
import ru.weather.weathertoday.busines.model.GeoCodingDataModel
import ru.weather.weathertoday.busines.model.toDataModel
import ru.weather.weathertoday.busines.model.toEntity


const val SAVED = 1
const val CURRENT = 0

class MenuRepository(api: ApiProvider) : BaseRepository<MenuRepository.Content>(api) {

    private val dbAccess = db.getGeoDataDao()

    fun getCities(name: String) {
        Log.d(TAG, "repo getCities: inserted : $name")
        api.provideGeoCodingApi().getCitiesByName(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                Content(it, CURRENT)
            }
            .subscribe({
                Log.d(TAG, "repo getCities Content : " + it.data[0].local_names)
                dataEmitter.onNext(it)
            }, {
                Log.d(TAG, "repo getCities Error : " + it.message.toString())
            })
    }

    fun add(data: GeoCodingDataModel) {
        getFavoritelListWith { dbAccess.add(data.toEntity()) }
    }

    fun remove(data: GeoCodingDataModel) {
        getFavoritelListWith { dbAccess.remove(data.toEntity()) }
    }

    fun updateFavorite() {
        getFavoritelListWith()
    }

    private fun getFavoritelListWith(daoQuery: (() -> Unit)? = null) {
        roomTransaction {
            daoQuery?.let { it() }
            Content(dbAccess.getAll().map { it.toDataModel() }, SAVED)
        }
    }


    data class Content(val data: List<GeoCodingDataModel>, val purpose: Int)


}