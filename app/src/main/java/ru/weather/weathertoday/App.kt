package ru.weather.weathertoday

import android.app.Application
import android.content.Intent
import android.util.Log
import ru.weather.weathertoday.activity.InitialActivity
import ru.weather.weathertoday.activity.TAG

const val APP_SETTINGS = "App_settings"
const val IS_STARTED_UP = "First_run"

class App : Application() {

    //todo link on respositiry

    var flagIsStartedUp : Boolean = false

    override fun onCreate() {
        super.onCreate()

        // todo init repo


        val preferences = getSharedPreferences(APP_SETTINGS, MODE_PRIVATE)
        flagIsStartedUp = preferences.contains(IS_STARTED_UP)
        //если нет такой пары ключ значение - создаст и по умолчанию false???

        if(!flagIsStartedUp){
            val intent = Intent(this, InitialActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        Log.d(TAG, "onCreate APP")
    }

}