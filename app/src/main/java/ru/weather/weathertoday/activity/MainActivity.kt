package ru.weather.weathertoday.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.weather.weathertoday.R
import ru.weather.weathertoday.busines.model.DailyWeatherModel
import ru.weather.weathertoday.busines.model.HourlyWeatherModel
import ru.weather.weathertoday.busines.model.WeatherDataModel
import ru.weather.weathertoday.presenters.MainPresenter
import ru.weather.weathertoday.view.*
import ru.weather.weathertoday.view.mainAdapters.AdapterDailyWeather
import ru.weather.weathertoday.view.mainAdapters.AdapterHourlyWeather

const val TAG = "GEO"
const val COORDINATES = "coordinates"

class MainActivity : MvpAppCompatActivity(), MainView {

    //-------------geo var ---------------------
    private val geoService by lazy { LocationServices.getFusedLocationProviderClient(this) }
    private val locationRequest by lazy { initLocationRequest() }
    private lateinit var mLocation: Location
//-------------geo var ---------------------

    //-----------------adapters var ----------------------
    private lateinit var adapterHourly: AdapterHourlyWeather
    private lateinit var adapterDaily: AdapterDailyWeather
//-----------------adapters var ----------------------


    private val mainPresenter by moxyPresenter { MainPresenter() }


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate MainActivity")

        initTestView()

        if (!intent.hasExtra(COORDINATES)) {
            geoService.requestLocationUpdates(locationRequest, geoCallback, null)
        } else {
            val coord = intent!!.extras!!.getBundle(COORDINATES)
            val loc = Location("")
            loc.latitude = coord!!.getString("lat")!!.toDouble()
            loc.longitude = coord.getString("lon")!!.toDouble()
            mLocation = loc
            mainPresenter.refresh(lat = mLocation.latitude.toString(), lon = mLocation.longitude.toString())
        }

        btn_main_menu.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, android.R.anim.fade_out)
        }

        initRvHourly()
        initRvDaily()

        //до обноления метосположения должно быть по моему
        mainPresenter.enable()

        //Обновление местоположения
        //geoService.requestLocationUpdates(locationRequest, geoCallback, null)
    }

    private fun initRvHourly() {
        adapterHourly = AdapterHourlyWeather()
        main_hourly_rc_list.layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        main_hourly_rc_list.adapter = adapterHourly
        main_hourly_rc_list.setHasFixedSize(true)
    }

    private fun initRvDaily() {
        adapterDaily = AdapterDailyWeather()
        main_daily_rc_list.apply {
            adapter = adapterDaily
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initTestView() {
        tv_main_city.text = "Moskow"
        tv_main_date.text = "01.01.2022"

        iv_weather_status.setImageDrawable(getDrawable(R.drawable.ic_oblako_24))
        tv_status_weather.text = "Облачно"
        tv_current_temperature.text = "25" + "\u00b0"
        min_temp_value.text = "19"
        max_temp_value.text = "32"

        iv_weather_image.setImageResource(R.mipmap.unionx4)
        davlenie_status.text = "15 mPa"
        vlazhnost_status.text = "50%"
        skorost_vetra_status.text = "15m/s"
        sunrise_status.text = "6:00"
        sunset_status.text = "20:00"

    }

//----------------------location code-----------------------

    //Инициализация locationRequest (настройка получения запроса местоположения)
    private fun initLocationRequest(): LocationRequest {
        val request = LocationRequest.create()
        return request.apply {
            interval = 30000 // ограничение по времени сколько будет ожидать обновления
            fastestInterval = 60000
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }
    }


    //CallBack Для обновления местоположения - после initLocationRequest
    // тут получаем наше местоположение mLocation
    // и отправляем его в презентер
    private val geoCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            for (location in p0.locations) {
                Log.d(TAG, "onLocationResult locations count: ${p0.locations.size}")
                mLocation = location
                //когда обновляется местоположение - передаем сразу в презентер
                mainPresenter.refresh(mLocation.latitude.toString(), mLocation.longitude.toString())
                Log.d(TAG, "onLocationResult:lat: ${location.latitude}; lon: ${location.longitude}")
            }
        }
    }


//----------------------location code-----------------------


    //---------------------------moxy code-----------------------------
    override fun displayLocation(data: String) {
        tv_main_city.text = data.toString()
        Log.d(TAG, "activity city data $data")
    }

    override fun displayCurrentData(data: WeatherDataModel) {
        data.apply {
            iv_weather_status.setImageResource(current.weather[0].icon.provideIcon())
            tv_current_temperature.text = StringBuilder().append(current.temp.toDegree()).append("\u00b0").toString()
            daily[0].temp.apply {
                min_temp_value.text = min.toDegree()
                max_temp_value.text = max.toDegree()
            }
            davlenie_status.text =
                StringBuilder().append(current.pressure.toString()).append("hPa").toString()
            vlazhnost_status.text =
                StringBuilder().append(current.humidity.toString()).append("%").toString()
            skorost_vetra_status.text =
                StringBuilder().append(current.wind_speed.toString()).append("m/s").toString()
            sunrise_status.text = current.sunrise.toDateFormatOf(HOUR_DOUBLE_DOT_MINUTE)
            sunset_status.text = current.sunset.toDateFormatOf(HOUR_DOUBLE_DOT_MINUTE)

            iv_weather_image.setImageResource(R.mipmap.unionx4) // todo добавить метод для изменения картинки
            tv_main_date.text = current.dt.toDateFormatOf(DAY_FULL_MONTH_NAME)
            tv_status_weather.text = current.weather[0].description

        }
    }

    override fun displayHourlyData(data: List<HourlyWeatherModel>) {
        (main_hourly_rc_list.adapter as AdapterHourlyWeather).updateData(data)
    }

    override fun displayDaylyData(data: List<DailyWeatherModel>) {
        (main_daily_rc_list.adapter as AdapterDailyWeather).updateData(data)
    }

    override fun displayError(error: Throwable) {}

    override fun setLoading(flag: Boolean) {}
    //---------------------------moxy code-----------------------------

}
