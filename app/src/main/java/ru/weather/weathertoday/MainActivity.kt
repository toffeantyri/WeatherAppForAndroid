package ru.weather.weathertoday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.weather.weathertoday.view.DailyItem
import ru.weather.weathertoday.view.HourlyItem
import ru.weather.weathertoday.view.mainAdapters.AdapterDailyWeather
import ru.weather.weathertoday.view.mainAdapters.AdapterHourlyWeather

class MainActivity : AppCompatActivity() {
    lateinit var adapterHourly : AdapterHourlyWeather
    lateinit var adapterDaily : AdapterDailyWeather

    lateinit var listHourly: ArrayList<HourlyItem>
    lateinit var listDaily: ArrayList<DailyItem>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_main_city.text = "Moskow"
        tv_main_date.text = "01.01.2022"

        iv_weather_status.setImageDrawable(getDrawable(R.drawable.ic_oblako_24))
        tv_status_weather.text = "Облачно"
        tv_current_temperature.text = "25"+"\u00b0"
        min_temp_value.text = "19"
        max_temp_value.text = "32"

        iv_weather_image.setImageResource(R.mipmap.unionx4)
        davlenie_status.text = "15 mPa"
        vlazhnost_status.text = "50%"
        skorost_vetra_status.text = "15m/s"
        sunrise_status.text = "6:00"
        sunset_status.text = "20:00"

        val d = HourlyItem("20:00","25",R.drawable.ic_oblako_24, "25" )
        val r = DailyItem("20 april", R.drawable.ic_oblako_24, "50", "25", "50")

        listHourly = arrayListOf(d,d,d,d,d,d,d,d,d,d,d,d,d)
        listDaily = arrayListOf(r,r,r,r,r,r,r,r,r,r,r)
        initRvHourly()
        initRvDaily()

    }

    fun initRvHourly(){
        adapterHourly = AdapterHourlyWeather(listHourly)
        main_hourly_rc_list.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        main_hourly_rc_list.adapter = adapterHourly
        main_hourly_rc_list.setHasFixedSize(true)
    }

    fun initRvDaily(){
        adapterDaily = AdapterDailyWeather(listDaily)
        main_daily_rc_list.apply {
            adapter = adapterDaily
            layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
        }
    }
}
