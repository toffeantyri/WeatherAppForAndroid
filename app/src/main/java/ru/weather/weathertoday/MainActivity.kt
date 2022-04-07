package ru.weather.weathertoday

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.media.audiofx.Equalizer
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_main.*
import ru.weather.weathertoday.view.DailyItem
import ru.weather.weathertoday.view.HourlyItem
import ru.weather.weathertoday.view.mainAdapters.AdapterDailyWeather
import ru.weather.weathertoday.view.mainAdapters.AdapterHourlyWeather

const val TAG = "GEO"
const val GEO_LOCATION_REQUEST_CODE_SUCCESS = 1

class MainActivity : AppCompatActivity() {
    lateinit var adapterHourly: AdapterHourlyWeather
    lateinit var adapterDaily: AdapterDailyWeather
    lateinit var listHourly: ArrayList<HourlyItem>
    lateinit var listDaily: ArrayList<DailyItem>

    //-------------geo var ---------------------
    private val geoService by lazy { LocationServices.getFusedLocationProviderClient(this) }
    private val locationRequest by lazy { initLocationRequest() }
    private lateinit var mLocation: Location

    lateinit var locationManager : LocationManager
    //-------------geo var ---------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //вызов этой функции будет в Initial Activity
        checkPermission()
        checkAndOpenOptionGpsIfGpsOff()


        initTestView()
        //add notNonull obj for adapters
        val d = HourlyItem("20:00", "25", R.drawable.ic_oblako_24, "25")
        val r = DailyItem("20 april", R.drawable.ic_oblako_24, "50", "25", "50")
        listHourly = arrayListOf(d, d, d, d, d, d, d, d, d, d, d, d, d)
        listDaily = arrayListOf(r, r, r, r, r, r, r, r, r, r, r)
        initRvHourly()
        initRvDaily()
        //---------------------------------------------------------------------------------------------------------------

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
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
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
    fun checkGpsStatus() : Boolean{
        locationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return  locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    //Если GPS отключен - откроется диалог и по кнопке Ок - откроются настройки включения GPS
    fun checkAndOpenOptionGpsIfGpsOff(){
        if(checkGpsStatus() == false) {
        MaterialAlertDialogBuilder(this)
            .setTitle("GPS отключен")
            .setMessage("Перейти настройкам GPS")
            .setPositiveButton("Ok"){ _,_ ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
                            }
            .setNegativeButton("cancel"){
                dialog,_ -> dialog.dismiss()
                Toast.makeText(this, "Текущее местоположение недоступно", Toast.LENGTH_SHORT).show()
            }.create().show()
        }
    }

//----------------------location code-----------------------

    //Инициализация loacationRequest
    private fun initLocationRequest(): LocationRequest {
        val request = LocationRequest.create()
        return request.apply {
            interval = 30000 // ограничение по времени сколько будет ожидать обновления
            fastestInterval = 15000
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }
    }


    //переопределение функции CallBack Для обновления местоположения - тут инициализируем наше местоположение mLocation
    //тут же должны быть методы/callBackи для презентера
    private val geoCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            for (location in p0.locations) {
                Log.d(TAG, "onLocationResult locations count: ${p0.locations.size}")
                mLocation = location
                //TODO будет вызов вызова нашего  Presenter
                Log.d(TAG, "onLocationResult locations: lat - ${location.latitude} ; lon - ${location.longitude} ")

            }
        }
    }



    @SuppressLint("MissingSuperCall") // что бы не ждал вызова super (это метод внутри callbacka )
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        //тут будет майн активити запуск
        Log.d(TAG, "Request code : $requestCode")
        //TODO запуст MainActivity
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    //это будет в initial ACTIVITY типа вход, там проверяется и запрашивается разрешение на доступ к местоположению

    //------------------initial Activity code--------------------
    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            MaterialAlertDialogBuilder(this)
                .setTitle("Нам нужны гео данные")
                .setMessage("Пожалуйства разрешите гео данные для продолжения работы приложения")
                .setPositiveButton("Ok") { _,_ ->
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        GEO_LOCATION_REQUEST_CODE_SUCCESS
                    )

                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
                        GEO_LOCATION_REQUEST_CODE_SUCCESS
                    )
                }
                .setNegativeButton("cancel") {
                    dialog,_ -> dialog.dismiss()
                }.create().show()
        }
    }
    //------------------initial Activity code--------------------


//----------------------location code-----------------------

}
