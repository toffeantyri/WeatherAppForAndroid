package ru.weather.weathertoday.busines.repositories

import androidx.room.Query
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.weather.weathertoday.busines.ApiProvider
import ru.weather.weathertoday.busines.model.GeoCodingDataModel


const val SAVED = 1
const val CURRENT = 0

class MenuRepository(api: ApiProvider) : BaseRepository<MenuRepository.Content>(api) {

    private val dbAccess = db.getGeoDataDao()

    fun getCities(name: String) {
        api.provideGeoCodingApi().getCitiesByName(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                Content(it, CURRENT)
            }
            .subscribe {
                dataEmitter.onNext(it)
            }
    }

    fun add(data: GeoCodingDataModel) {
        getFavoritelListWith{ dbAccess.add(data)} // todo map.toEntity
    }

    fun remove(data: GeoCodingDataModel) {
        getFavoritelListWith { dbAccess.remove(data) } //todo
    }

    fun updateFavorite() {
        getFavoritelListWith()
    }

    private fun getFavoritelListWith(daoQuery: (() -> Unit)? = null) {
        roomTransaction {
            daoQuery?.let { it() }
            Content(dbAccess.getAll().map {   }, SAVED)  /* todo it.mapToModel*/
        }
    }


    data class Content(val data: List<GeoCodingDataModel>, val purpose: Int)


}