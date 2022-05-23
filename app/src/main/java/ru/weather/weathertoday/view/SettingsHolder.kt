package ru.weather.weathertoday.view

import android.content.SharedPreferences
import androidx.annotation.IdRes
import ru.weather.weathertoday.R
import java.util.*
import kotlin.math.roundToInt

const val TEMP = "Temp"
const val WIND_SPEED = "Wind speed"
const val PRESSURE = "Pressure"

object SettingsHolder {

    lateinit var preferences: SharedPreferences
    var temp = Setting.TEMP_CELSIUS
    var windSpeed = Setting.WIND_SPEED_MS
    var pressure = Setting.PRESSURE_HPA

    fun onCreate(prefs: SharedPreferences) {
        preferences = prefs
        temp = getSettings(preferences.getInt(TEMP, C)) // C - Default Value
        windSpeed = getSettings(preferences.getInt(WIND_SPEED, MS))
        pressure = getSettings(preferences.getInt(PRESSURE, HPA))
    }

    fun onDestroy() {
        val editor = preferences.edit()
        editor.putInt(TEMP, temp.prefConst)
        editor.putInt(WIND_SPEED, windSpeed.prefConst)
        editor.putInt(PRESSURE, pressure.prefConst)
        editor.apply()
    }

    private fun getSettings(@IdRes id: Int) = when (id) {
        C -> Setting.TEMP_CELSIUS
        F -> Setting.TEMP_FAHRENHEIT
        MS -> Setting.WIND_SPEED_MS
        KMH -> Setting.WIND_SPEED_KMH
        MN_HG -> Setting.PRESSURE_MN_HG
        HPA -> Setting.PRESSURE_HPA
        else -> throw InputMismatchException()
    }


    const val C = 1
    const val F = 2
    const val MS = 3
    const val KMH = 4
    const val MN_HG = 5
    const val HPA = 6

    enum class Setting(@IdRes val checkedViewId: Int, @IdRes val measureUnitString: Int, val prefConst: Int) {

        TEMP_FAHRENHEIT(R.id.degree_f, R.string.temp_f, F) {
            override fun getValue(initValue: Double): String = valueToString { (initValue - 273.15) * (9 / 5) + 32 }
        },
        TEMP_CELSIUS(R.id.degree_c, R.string.temp_c, C) {
            override fun getValue(initValue: Double): String = valueToString { (initValue - 273.15) }
        },
        WIND_SPEED_MS(R.id.degree_ms, R.string.ms, MS) {
            override fun getValue(initValue: Double): String = valueToString { (initValue) }
        },
        WIND_SPEED_KMH(R.id.degree_kmh, R.string.kmh, KMH) {
            override fun getValue(initValue: Double): String = valueToString { (initValue * 3.6) }
        },
        PRESSURE_MN_HG(R.id.degree_mnHa, R.string.mnhg, MN_HG) {
            override fun getValue(initValue: Double): String = valueToString { (initValue / 1.33322387415) }
        },
        PRESSURE_HPA(R.id.degree_hPa, R.string.hpa, HPA) {
            override fun getValue(initValue: Double): String = valueToString { (initValue) }
        }
        ;


        abstract fun getValue(initValue: Double): String
        protected fun valueToString(formula: () -> Double) = formula().roundToInt().toString()
    }


}