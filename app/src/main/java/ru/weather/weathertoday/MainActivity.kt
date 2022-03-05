package ru.weather.weathertoday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

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



    }
}
