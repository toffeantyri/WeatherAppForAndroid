package ru.weather.weathertoday.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.material.button.MaterialButtonToggleGroup
import kotlinx.android.synthetic.main.activity_setting.*
import ru.weather.weathertoday.R
import ru.weather.weathertoday.view.SettingsHolder

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        sett_toolbar.setNavigationOnClickListener { onBackPressed() }

        setSavedSetting()

        listOf(toogle_gr_temp, toogle_gr_wind_speed, toogle_gr_pressure).forEach {
            it.addOnButtonCheckedListener(ToggleButtonClickListener)
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        SettingsHolder.onDestroy()
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, R.anim.slide_out_right)
    }


    private fun setSavedSetting() {
        toogle_gr_temp.check(SettingsHolder.temp.checkedViewId)
        toogle_gr_wind_speed.check(SettingsHolder.windSpeed.checkedViewId)
        toogle_gr_pressure.check(SettingsHolder.pressure.checkedViewId)
    }


    private object ToggleButtonClickListener : MaterialButtonToggleGroup.OnButtonCheckedListener {
        override fun onButtonChecked(viewGorup: MaterialButtonToggleGroup?, checkedId: Int, isChecked: Boolean) {
            when (checkedId to isChecked) {
                R.id.degree_c to true -> SettingsHolder.temp = SettingsHolder.Setting.TEMP_CELSIUS
                R.id.degree_f to true -> SettingsHolder.temp = SettingsHolder.Setting.TEMP_FAHRENHEIT
                R.id.degree_ms to true -> SettingsHolder.windSpeed = SettingsHolder.Setting.WIND_SPEED_MS
                R.id.degree_kmh to true -> SettingsHolder.windSpeed = SettingsHolder.Setting.WIND_SPEED_KMH
                R.id.degree_mnHa to true -> SettingsHolder.pressure = SettingsHolder.Setting.PRESSURE_MN_HG
                R.id.degree_hPa to true -> SettingsHolder.pressure = SettingsHolder.Setting.PRESSURE_HPA
            }
        }

    }
}
