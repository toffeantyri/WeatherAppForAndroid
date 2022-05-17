package ru.weather.weathertoday.activity


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_menu.*
import moxy.MvpAppCompatActivity
import ru.weather.weathertoday.R
import ru.weather.weathertoday.busines.model.GeoCodingDataModel
import ru.weather.weathertoday.view.MenuView
import ru.weather.weathertoday.view.createObservable
import ru.weather.weathertoday.view.mainAdapters.COORDINATES
import ru.weather.weathertoday.view.mainAdapters.CityListAdapter
import java.util.concurrent.TimeUnit

class MenuActivity : MvpAppCompatActivity(), MenuView {

    //todo add presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)


        //todo presenter.enable()
        //todo presenter.getFavoriteList()

        initCityList(predictions)
        initCityList(favorites)

        //Во viewUtil метод возвращающий Flowable
        //тут когда начинаешь печатеть - doOnNext - включает видимость загрузки
        //задержка в милисекундах и затем подписчик делает запрос к Favorite и ответ потом придёт в ? листFavorite?
        search_field.createObservable()
            .doOnNext { setLoading(true) }
            .debounce(700, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                //todo if it notEmpty presenter.getFavorite()
            }

    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, R.anim.slide_out_left)
    }

    //-----------------------------moxy view-----------------------------------

    override fun setLoading(flag: Boolean) {
        loading.isActivated = true
        loading.visibility = if (flag) View.VISIBLE else View.GONE
    }

    override fun fillPredicitonList(data: List<GeoCodingDataModel>) {
        //todo
    }

    override fun fillFavoriteList(data: List<GeoCodingDataModel>) {
        //todo
    }

    //-----------------------------moxy view-----------------------------------


    private fun initCityList(rv: RecyclerView) {
        val cityAdapter = CityListAdapter()
        cityAdapter.clickListener = searchItemClickListener
        rv.apply {
            adapter = cityAdapter
            layoutManager = object : LinearLayoutManager(this@MenuActivity, VERTICAL, false) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }
            setHasFixedSize(true)
        }
    }


    private val searchItemClickListener = object : CityListAdapter.SearchItemClickListener {
        override fun addToFavorite(item: GeoCodingDataModel) {
            //todo presenter saveLocation
        }

        override fun removeFromFavorite(item: GeoCodingDataModel) {
            //todo presenter removeLocation
        }

        override fun showWeatherIn(item: GeoCodingDataModel) {
            val intent: Intent = Intent(this@MenuActivity, MainActivity::class.java)
            val bundle = Bundle()
            bundle.putString("lat", item.lat.toString())
            bundle.putString("lon", item.lon.toString())
            intent.putExtra(COORDINATES, bundle)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, R.anim.slide_out_left)
        }
    }


}
