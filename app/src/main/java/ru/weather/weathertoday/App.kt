package ru.weather.weathertoday

import android.app.Application
import android.content.Intent
import android.util.Log

const val APP_SETTINGS = "App_settings"
const val FIRST_RUN = "First_run"

class App : Application() {

    //todo link on respositiry

    var flagAlreadyRunning : Boolean = false

    override fun onCreate() {
        super.onCreate()

        // todo init repo

        Log.d(TAG, "onCreate APP")
        val preferences = getSharedPreferences(APP_SETTINGS, MODE_PRIVATE)
        flagAlreadyRunning = preferences.contains(FIRST_RUN)
        //если нет такой пары ключ значение - создаст и по умолчанию false???

        if(!flagAlreadyRunning){
            val intent = Intent(this, InitialActivity::class.java)
            preferences.edit().apply {
                putBoolean(FIRST_RUN, true)
                apply()
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}