package ru.weather.weathertoday.activity


import android.os.Bundle
import moxy.MvpAppCompatActivity
import ru.weather.weathertoday.R

class MenuActivity : MvpAppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

    }
}
