package ru.weather.weathertoday

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.weather.weathertoday.busined.model.DaylyWeatherModel
import ru.weather.weathertoday.busined.model.HourlyWeatherModel
import ru.weather.weathertoday.busined.model.WeatherData
import ru.weather.weathertoday.presenters.MainPresenter
import ru.weather.weathertoday.view.MainView
import ru.weather.weathertoday.view.mainAdapters.DailyItem
import ru.weather.weathertoday.view.mainAdapters.HourlyItem
import ru.weather.weathertoday.view.mainAdapters.AdapterDailyWeather
import ru.weather.weathertoday.view.mainAdapters.AdapterHourlyWeather

const val TAG = "GEO"

class MainActivity : MvpAppCompatActivity(), MainView {

//-------------geo var ---------------------
    private val geoService by lazy { LocationServices.getFusedLocationProviderClient(this) }
    private val locationRequest by lazy { initLocationRequest() }
    private lateinit var mLocation: Location
    lateinit var locationManager: LocationManager
//-------------geo var ---------------------

    //-----------------adapters var ----------------------
    lateinit var adapterHourly: AdapterHourlyWeather
    lateinit var adapterDaily: AdapterDailyWeather
    lateinit var listHourly: ArrayList<HourlyItem>
    lateinit var listDaily: ArrayList<DailyItem>
//-----------------adapters var ----------------------


    private val mainPresenter by moxyPresenter { MainPresenter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate MainActivity")


        initTestView()


        initRvHourly()
        initRvDaily()
        //---------------------------------------------------------------------------------------------------------------


        //до обноления метосположения
        mainPresenter.enable()

        //Обновление местоположения
        geoService.requestLocationUpdates(locationRequest, geoCallback, null)


    }

    fun initRvHourly() {
        adapterHourly = AdapterHourlyWeather()
        main_hourly_rc_list.layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        main_hourly_rc_list.adapter = adapterHourly
        main_hourly_rc_list.setHasFixedSize(true)
    }

    fun initRvDaily() {
        adapterDaily = AdapterDailyWeather()
        main_daily_rc_list.apply {
            adapter = adapterDaily
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }
    }

    fun initTestView() {
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


    //проверка включен ли GPS
    fun checkGpsStatus(): Boolean {
        locationManager =
            applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    //Если GPS отключен - откроется диалог и по кнопке Ок - откроются настройки включения GPS
    fun checkAndOpenOptionGpsIfGpsOff() {
        if (checkGpsStatus() == false) {
            MaterialAlertDialogBuilder(this)
                .setTitle("GPS отключен")
                .setMessage("Перейти настройкам GPS")
                .setPositiveButton("Ok") { _, _ ->
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)
                }
                .setNegativeButton("cancel") { dialog, _ ->
                    dialog.dismiss()
                    Toast.makeText(this, "Текущее местоположение недоступно", Toast.LENGTH_SHORT)
                        .show()
                }.create().show()
        }
    }

//----------------------location code-----------------------

    //Инициализация locationRequest (настройка получения запроса местоположения)
    private fun initLocationRequest(): LocationRequest {
        val request = LocationRequest.create()
        return request.apply {
            interval = 30000 // ограничение по времени сколько будет ожидать обновления
            fastestInterval = 15000
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
                mainPresenter.refresh(mLocation.latitude.toString(), mLocation.longitude.toString())
                Log.d(TAG, "onLocationResult:lat: ${location.latitude}; lon: ${location.longitude}")
            }
        }
    }





//----------------------location code-----------------------


    //---------------------------moxy code-----------------------------
    override fun displayLocation(data: String) {
        tv_main_city.text = data
    }

    override fun displayCurrentData(data: WeatherData) {
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

    override fun displayHourlyData(data: List<HourlyWeatherModel>) {
        (main_hourly_rc_list.adapter as AdapterHourlyWeather).updateData(data)
    }

    override fun displayDaylyData(data: List<DaylyWeatherModel>) {
        (main_daily_rc_list.adapter as AdapterDailyWeather).updateData(data)
    }

    override fun displayError(error: Throwable) {

    }

    override fun setLoading(flag: Boolean) {

    }
    //---------------------------moxy code-----------------------------

}
