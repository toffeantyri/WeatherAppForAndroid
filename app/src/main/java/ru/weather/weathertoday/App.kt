package ru.weather.weathertoday

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.room.Room
import ru.weather.weathertoday.activity.InitialActivity
import ru.weather.weathertoday.activity.TAG
import ru.weather.weathertoday.busines.room.OpenWeatherDataBase
import ru.weather.weathertoday.view.SettingsHolder

const val APP_SETTINGS = "App_settings"
const val IS_STARTED_UP = "First_run"

class App : Application() {

    companion object {
        //todo сделать DB как singleton
        lateinit var db: OpenWeatherDataBase
    }

    private var flagIsStartedUp: Boolean = false

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(this, OpenWeatherDataBase::class.java, "OpenWeatherDB")
            .fallbackToDestructiveMigration() // todo что бы не настраивать миграцию??? Убрать к релизу
            .build()


        val preferences = getSharedPreferences(APP_SETTINGS, MODE_PRIVATE)
        flagIsStartedUp = preferences.contains(IS_STARTED_UP)
        //если нет такой пары ключ значение - создаст и по умолчанию false???

        SettingsHolder.onCreate(preferences)


        if (!flagIsStartedUp) {
            val intent = Intent(this, InitialActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        Log.d(TAG, "onCreate APP")
    }

}